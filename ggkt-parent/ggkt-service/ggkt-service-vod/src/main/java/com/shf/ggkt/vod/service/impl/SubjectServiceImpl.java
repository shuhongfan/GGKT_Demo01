package com.shf.ggkt.vod.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.exception.GgktException;
import com.shf.ggkt.model.vod.Subject;
import com.shf.ggkt.vo.vod.SubjectEeVo;
import com.shf.ggkt.vod.listener.SubjectListener;
import com.shf.ggkt.vod.mapper.SubjectMapper;
import com.shf.ggkt.vod.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    /**
     * 课程分类列表
     * 懒加载，每一次查询一层数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Subject> selectSubjectList(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);

//        subjectList遍历，得到每个subject对象，判断是否有下一层数据，有hashChildren=true
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList) {
//            获取subject的id值
            Long subjectId = subject.getId();
//            查询
            boolean isChild = this.isChildren(subjectId);
//            封装到对象里面
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }

    /**
     * 课程分类导出
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
//            设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

//            查询课程分类表所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            List<SubjectEeVo> subjectEeVos = new ArrayList<>(subjectList.size());
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVos.add(subjectEeVo);
            }

//            EasyExcel写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVos);

        } catch (Exception e) {
            throw new GgktException(20001, "导出失败");
        }
    }

    /**
     * 课程分类导入
     *
     * @param file
     */
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(
                            file.getInputStream(),
                            SubjectEeVo.class,
                            subjectListener)
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new GgktException(200001, "导入失败");
        }
    }

    /**
     * 判断是否有下一层数据
     *
     * @param subjectId
     * @return
     */
    private boolean isChildren(Long subjectId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", subjectId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}

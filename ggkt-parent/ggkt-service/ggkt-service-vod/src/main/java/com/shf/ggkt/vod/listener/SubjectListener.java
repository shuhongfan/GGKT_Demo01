package com.shf.ggkt.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.shf.ggkt.model.vod.Subject;
import com.shf.ggkt.vo.vod.SubjectEeVo;
import com.shf.ggkt.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    //    注入mapper
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 一行一行读取excel内容，把每行内容封装到user对象
     * 从excel第二行开始读取
     * @param subjectEeVo
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo, subject);
//        添加
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

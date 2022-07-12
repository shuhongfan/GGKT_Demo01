package com.shf.ggkt.vod.controller;


import com.shf.ggkt.model.vod.Subject;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-09
 */
@Api(tags = "课程分类管理")
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 课程分类列表
     * 懒加载，每一次查询一层数据
     * @param id
     * @return
     */
    @ApiOperation("课程分类列表")
    @GetMapping("getChildrenSubject/{id}")
    public Result getChildSubject(@PathVariable Long id){
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.ok(list);
    }

    /**
     * 课程分类导出
     * @param response
     */
    @ApiOperation("课程分类导出")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    /**
     * 课程分类导入
     * @param file
     * @return
     */
    @ApiOperation("课程分类导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.ok(null);
    }
}


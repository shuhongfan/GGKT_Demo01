package com.shf.ggkt.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ggkt.model.vod.Course;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.vod.CourseFormVo;
import com.shf.ggkt.vo.vod.CoursePublishVo;
import com.shf.ggkt.vo.vod.CourseQueryVo;
import com.shf.ggkt.vod.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 点播课程列表
     * @param page
     * @param limit
     * @param courseQueryVo
     * @return
     */
    @ApiOperation("点播课程列表")
    @GetMapping("{page}/{limit}")
    public Result courseList(
            @PathVariable Long page,
            @PathVariable Long limit,
            CourseQueryVo courseQueryVo
    ){
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.findPageCourse(coursePage, courseQueryVo);
        return Result.ok(map);
    }

    /**
     * 添加课程基本信息
     * @param courseFormVo
     * @return
     */
    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    /**
     * 根据id获取课程信息
     * @param id
     * @return
     */
    @ApiOperation("根据id获取课程信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }

    /**
     * 修改课程信息
     * @param courseFormVo
     * @return
     */
    @ApiOperation("修改课程信息")
    @PutMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseById(courseFormVo);
//        返回课程id
        return Result.ok(courseFormVo.getId());
    }

    /**
     * id查询发布课程信息
     * @param id
     * @return
     */
    @ApiOperation("id查询发布课程信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    /**
     * 课程最终发布
     * @param id
     * @return
     */
    @ApiOperation("课程最终发布")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourse(@PathVariable Long id){
        courseService.publishCourse(id);
        return Result.ok();
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseId(id);
        return Result.ok();
    }

    /**
     * 查询所有课程
     * @return
     */
    @GetMapping("findAll")
    public Result findAll() {
        List<Course> list = courseService.findList();
        return Result.ok(list);
    }
}


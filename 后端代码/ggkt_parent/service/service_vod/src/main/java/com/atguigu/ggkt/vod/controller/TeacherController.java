package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-13
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value="/admin/vod/teacher")
//@CrossOrigin //跨域
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // http://localhost:8301/admin/vod/teacher/findAll
    //1 查询所有讲师
//    @ApiOperation("查询所有讲师")
//    @GetMapping("findAll")
//    public List<Teacher> findAllTeacher() {
//        //调用service方法
//        List<Teacher> list = teacherService.list();
//        return list;
//    }
    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher() {
        //模拟异常
//        try {
//            int i = 10/0;
//        }catch (Exception e) {
//            //抛出异常
//            throw new GgktException(201,"执行自定义异常处理GgktException");
//        }
        //调用service方法
        List<Teacher> list = teacherService.list();
        return Result.ok(list).message("查询数据成功");
    }

    // remove/1
    //2 逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "ID", required = true)
                                     @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //3 条件查询分页
    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        //创建page对象
        Page<Teacher> pageParam = new Page<>(current,limit);
        //判断teacherQueryVo对象是否为空
        if(teacherQueryVo == null) {//查询全部
            IPage<Teacher> pageModel =
                    teacherService.page(pageParam,null);
            return Result.ok(pageModel);
        } else {
            //获取条件值，
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            //进行非空判断，条件封装
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(name)) {
                wrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(level)) {
                wrapper.eq("level",level);
            }
            if(!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date",joinDateBegin);
            }
            if(!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date",joinDateEnd);
            }
            //调用方法分页查询
            IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);
            //返回
            return Result.ok(pageModel);
        }
    }

    //4 添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //5 修改-根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    //6 修改-最终实现
    // {...}
    @ApiOperation("修改最终实现")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //7 批量删除讲师
    // json数组 [1,2,3]
    @ApiOperation("批量删除讲师")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //根据id查询 远程调用
    @ApiOperation("根据id查询")
    @GetMapping("inner/getTeacher/{id}")
    public Teacher getTeacherInfo(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return teacher;
    }
}


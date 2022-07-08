package com.shf.ggkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ggkt.exception.GgktException;
import com.shf.ggkt.model.vod.Teacher;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.vod.TeacherQueryVo;
import com.shf.ggkt.vod.service.TeacherService;
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
 * @author shuhongfan
 * @since 2022-07-07
 */
@Api(tags = "讲师管理接口") // 定义在类上
@RestController
@CrossOrigin // 开启跨域
@RequestMapping("/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    //    1.查询所有讲师
    @ApiOperation("查询所有讲师")  // 定义在方法上
    @GetMapping("findAll")
    public Result<List<Teacher>> findAllTeacher() {
        try { // 业务中需要位置抛出自定义异常
            int a = 10/0;
        }catch(Exception e) {
            throw new GgktException(20001,"出现自定义异常");
        }
        List<Teacher> list = teacherService.list();
        return !StringUtils.isEmpty(list) ? Result.ok(list) : Result.fail(null);
    }

    //    2.逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true) // 定义在参数上
            @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

    //    3 条件查询分页
    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(
            @ApiParam(name = "current", value = "当前页码", required = true) @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
            @ApiParam(name = "teacherVo", value = "查询对象", required = false) @RequestBody(required = false) TeacherQueryVo teacherQueryVo // 1.提交是数据是JSON数据  2.条件值可以为空  3.POST方式提交
    ) {
//        创建page对象,传递当前页和每页记录数
        Page<Teacher> pageParam = new Page<>(current, limit);
//        判断teacherQueryVo对象是否为空
        if (teacherQueryVo == null) { // 查询全部
            Page<Teacher> teacherPage = teacherService.page(pageParam, null);
            return Result.ok(teacherPage).message("查询数据成功");
        } else {
//            获取条件值
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

//            进行非空判断，条件封装
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date", joinDateEnd);
            }

//            调用方法分页查询
            Page<Teacher> pageModel = teacherService.page(pageParam, wrapper);
            return Result.ok(pageModel).message("查询数据成功");
        }
    }

    //    4. 添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        return isSuccess ? Result.ok(teacher) : Result.fail(null);
    }

    //    5. 修改-根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return !StringUtils.isEmpty(teacher) ? Result.ok(teacher) : Result.fail(null);
    }

    //    6.  修改-最终实现
    @ApiOperation("修改最终实现")
    @PutMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

    //    7.批量删除讲师
    @ApiOperation("批量删除讲师")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

}


package com.shf.ggkt.live.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ggkt.live.service.LiveCourseAccountService;
import com.shf.ggkt.live.service.LiveCourseService;
import com.shf.ggkt.model.live.LiveCourse;
import com.shf.ggkt.model.live.LiveCourseAccount;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.live.LiveCourseConfigVo;
import com.shf.ggkt.vo.live.LiveCourseFormVo;
import com.shf.ggkt.vo.live.LiveCourseVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 直播课程表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
@RestController
@RequestMapping(value = "/admin/live/liveCourse")
public class LiveCourseController {
    @Autowired
    private LiveCourseService liveCourseService;

    @Autowired
    private LiveCourseAccountService liveCourseAccountService;

    /**
     * 直播课程列表
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<LiveCourse> pageParam = new Page<>(page, limit);
        Page<LiveCourse> pageModel = liveCourseService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    /**
     * 直播课程添加
     *
     * @param liveCourseFormVo
     * @return
     */
    @ApiOperation("直播课程添加")
    @PostMapping("save")
    public Result save(@RequestBody LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.saveLive(liveCourseFormVo);
        return Result.ok();
    }

    /**
     * 删除直播课程
     *
     * @param id
     * @return
     */
    @ApiOperation("删除直播课程")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        liveCourseService.removeLive(id);
        return Result.ok();
    }

    /**
     * 根据id查询直播课程基本信息
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询直播课程基本信息")
    @GetMapping("get/{id}")
    public Result<LiveCourse> get(@PathVariable Long id) {
        LiveCourse liveCourse = liveCourseService.getById(id);
        return Result.ok(liveCourse);
    }

    /**
     * 根据id查询直播课程基本信息和描述信息
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询直播课程基本信息和描述信息")
    @GetMapping("getInfo/{id}")
    public Result<LiveCourseFormVo> getInfo(@PathVariable Long id) {
        return Result.ok(liveCourseService.getLiveCourseFormVo(id));
    }

    /**
     * 更新直播课程方法
     *
     * @param liveCourseVo
     * @return
     */
    @ApiOperation(value = "更新直播课程方法")
    @PutMapping("update")
    public Result updateById(@RequestBody LiveCourseFormVo liveCourseVo) {
        liveCourseService.updateLiveById(liveCourseVo);
        return Result.ok(null);
    }

    /**
     * 获取直播账号信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取直播账号信息")
    @GetMapping("getLiveCourseAccount/{id}")
    public Result getLiveCourseAccount(@PathVariable Long id) {
        LiveCourseAccount liveCourseAccount = liveCourseAccountService.getLiveCourseAccountCourseId(id);
        return Result.ok(liveCourseAccount);
    }

    /**
     * 获取直播配置
     *
     * @param id
     * @return
     */
    @ApiOperation("获取直播配置")
    @GetMapping("getCourseConfig/{id}")
    public Result getCourseConfig(@PathVariable Long id) {
        LiveCourseConfigVo liveCourseConfigVo = liveCourseService.getCourseConfig(id);
        return Result.ok(liveCourseConfigVo);
    }

    /**
     * 修改直播配置
     * @param liveCourseConfigVo
     * @return
     */
    @ApiOperation("修改直播配置")
    @PutMapping("updateConfig")
    public Result updateConfig(@RequestBody LiveCourseConfigVo liveCourseConfigVo) {
        liveCourseService.updateConfig(liveCourseConfigVo);
        return Result.ok();
    }

    /**
     * 获取最近的直播
     * @return
     */
    @ApiOperation(value = "获取最近的直播")
    @GetMapping("findLatelyList")
    public Result findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = liveCourseService.findLatelyList();
        return Result.ok(liveCourseVoList);
    }
}


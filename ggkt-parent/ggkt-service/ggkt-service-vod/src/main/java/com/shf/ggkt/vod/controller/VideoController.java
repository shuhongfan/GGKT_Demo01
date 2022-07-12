package com.shf.ggkt.vod.controller;


import com.shf.ggkt.model.vod.Video;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
@Api("课程小节（课时）")
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Video video = videoService.getById(id);
        return Result.ok(video);
    }

    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody Video video) {
        boolean isSuccess = videoService.save(video);
        return Result.ok(isSuccess);
    }

    @ApiOperation("修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Video video){
        videoService.updateById(video);
        return Result.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        videoService.removeVideoById(id);
        return Result.ok();
    }
}


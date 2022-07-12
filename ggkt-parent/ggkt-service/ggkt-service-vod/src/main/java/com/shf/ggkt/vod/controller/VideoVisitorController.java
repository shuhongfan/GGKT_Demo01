package com.shf.ggkt.vod.controller;


import com.shf.ggkt.result.Result;
import com.shf.ggkt.vod.service.VideoVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-11
 */
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod/videoVisitor")
public class VideoVisitorController {
    @Autowired
    private VideoVisitorService videoVisitorService;

    /**
     * 课程统计的接口
     * @param courseId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(
            @PathVariable Long courseId,
            @PathVariable String startDate,
            @PathVariable String endDate
    ) {
        Map<String,Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }
}


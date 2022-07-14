package com.shf.ggkt.client.course;

import com.shf.ggkt.model.vod.Course;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ggkt-service-vod")
public interface CourseFeignClient {

    /**
     * 根据关键字查询课程
     * @param keyword
     * @return
     */
    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(@PathVariable String keyword);

    /**
     * 根据ID查询课程
     *
     * @param courseId
     * @return
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("/api/vod/course/inner/getById/{courseId}")
    public Course getById(@PathVariable Long courseId);

}

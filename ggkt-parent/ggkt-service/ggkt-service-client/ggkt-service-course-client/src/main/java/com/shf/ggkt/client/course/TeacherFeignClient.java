package com.shf.ggkt.client.course;

import com.shf.ggkt.model.vod.Teacher;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ggkt-service-vod")
public interface TeacherFeignClient {

    @ApiOperation("根据id查询")
    @GetMapping("/admin/vod/teacher/inner/getTeacher/{id}")
    public Teacher getTeacherInfo(@PathVariable Long id);
}

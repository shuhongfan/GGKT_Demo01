package com.shf.ggkt.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.live.LiveCourseDescription;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseDescriptionService extends IService<LiveCourseDescription> {

    LiveCourseDescription getLiveCourseById(Long id);
}

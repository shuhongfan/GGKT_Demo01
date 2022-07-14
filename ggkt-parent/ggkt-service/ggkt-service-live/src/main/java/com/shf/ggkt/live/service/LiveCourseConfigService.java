package com.shf.ggkt.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.live.LiveCourseConfig;

/**
 * <p>
 * 直播课程配置表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    LiveCourseConfig getCourseConfigCourseId(Long id);
}

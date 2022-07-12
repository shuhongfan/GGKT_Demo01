package com.shf.ggkt.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.vod.VideoVisitor;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-11
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}

package com.shf.ggkt.live.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.live.LiveCourse;
import com.shf.ggkt.vo.live.LiveCourseConfigVo;
import com.shf.ggkt.vo.live.LiveCourseFormVo;
import com.shf.ggkt.vo.live.LiveCourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直播课程表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseService extends IService<LiveCourse> {

    Page<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    Boolean saveLive(LiveCourseFormVo liveCourseFormVo);

    void removeLive(Long id);

    LiveCourseFormVo getLiveCourseFormVo(Long id);

    void updateLiveById(LiveCourseFormVo liveCourseVo);

    LiveCourseConfigVo getCourseConfig(Long id);

    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    List<LiveCourseVo> findLatelyList();

    JSONObject getAccessToken(Long id, Long userId);

    Map<String, Object> getInfoById(Long courseId);
}

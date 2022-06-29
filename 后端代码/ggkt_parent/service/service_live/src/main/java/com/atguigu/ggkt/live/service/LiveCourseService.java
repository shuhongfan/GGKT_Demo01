package com.atguigu.ggkt.live.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.model.live.LiveCourse;
import com.atguigu.ggkt.vo.live.LiveCourseConfigVo;
import com.atguigu.ggkt.vo.live.LiveCourseFormVo;
import com.atguigu.ggkt.vo.live.LiveCourseQueryVo;
import com.atguigu.ggkt.vo.live.LiveCourseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直播课程表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-09
 */
public interface LiveCourseService extends IService<LiveCourse> {

    //直播课程列表
    IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    //直播课程添加
    void saveLive(LiveCourseFormVo liveCourseFormVo);

    //删除直播课程
    void removeLive(Long id);

    //id查询直播课程基本信息和描述信息
    LiveCourseFormVo getLiveCourseVo(Long id);

    //更新
    void updateLiveById(LiveCourseFormVo liveCourseFormVo);

    //获取直播配置信息
    LiveCourseConfigVo getCourseConfig(Long id);

    //修改配置
    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    //获取最近的直播
    List<LiveCourseVo> getLatelyList();

    //获取用户access_token
    JSONObject getAccessToken(Long id, Long userId);

    //根据ID查询课程
    Map<String, Object> getInfoById(Long courseId);
}

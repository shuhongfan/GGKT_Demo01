package com.shf.ggkt.vod.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.model.vod.Video;
import com.shf.ggkt.vod.mapper.VideoMapper;
import com.shf.ggkt.vod.service.VideoService;
import com.shf.ggkt.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    /**
     * 根据课程id删除小节,删除所有小节视频
     * @param id
     */
    @Override
    public void removeVideoByCourseId(Long id) {
//        根据课程id查询课程所有小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Video> videoList = baseMapper.selectList(wrapper);

//        遍历所有小节集合得到每个小节，获取每个小节视频id
        for (Video video : videoList) {
            //        判断视频id是否为空，不为空，删除腾讯云视频
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        }

//        根据课程id删除课程所有小节
        baseMapper.delete(wrapper);
    }

    /**
     * 根据课程id删除小节，包括所有小节视频
     * @param id
     */
    @Override
    public void removeVideoById(Long id) {
//        id查询小节
        Video video = baseMapper.selectById(id);
//        获取video里面视频id
        String videoSourceId = video.getVideoSourceId();
//        判断视频id是否为空
        if (!StringUtils.isEmpty(videoSourceId)) {
//            视频id不为空，调用方法根据视频id删除腾讯云中的视频
            vodService.removeVideo(videoSourceId);
        }
//        根据id删除小节
        baseMapper.deleteById(id);
    }
}

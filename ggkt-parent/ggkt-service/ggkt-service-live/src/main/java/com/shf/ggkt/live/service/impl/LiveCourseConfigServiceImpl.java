package com.shf.ggkt.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.live.mapper.LiveCourseConfigMapper;
import com.shf.ggkt.live.service.LiveCourseConfigService;
import com.shf.ggkt.model.live.LiveCourseConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播课程配置表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
@Service
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig> implements LiveCourseConfigService {

    @Override
    public LiveCourseConfig getCourseConfigCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseConfig::getLiveCourseId, id);
        LiveCourseConfig liveCourseConfig = baseMapper.selectOne(wrapper);
        return liveCourseConfig;
    }
}

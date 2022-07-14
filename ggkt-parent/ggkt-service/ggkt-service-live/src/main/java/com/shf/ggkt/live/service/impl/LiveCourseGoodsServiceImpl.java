package com.shf.ggkt.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.live.mapper.LiveCourseGoodsMapper;
import com.shf.ggkt.live.service.LiveCourseGoodsService;
import com.shf.ggkt.model.live.LiveCourseGoods;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods> implements LiveCourseGoodsService {

    /**
     * 查询直播课程商品列表
     * @param id
     * @return
     */
    @Override
    public List<LiveCourseGoods> getGoodsListCourseId(Long id) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<LiveCourseGoods>()
                        .eq(LiveCourseGoods::getLiveCourseId, id));
    }
}

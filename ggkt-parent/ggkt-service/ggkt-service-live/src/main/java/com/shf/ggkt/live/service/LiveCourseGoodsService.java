package com.shf.ggkt.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.live.LiveCourseGoods;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseGoodsService extends IService<LiveCourseGoods> {

    List<LiveCourseGoods> getGoodsListCourseId(Long id);
}

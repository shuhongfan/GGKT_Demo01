package com.shf.ggkt.activity.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.activity.CouponInfo;
import com.shf.ggkt.model.activity.CouponUse;
import com.shf.ggkt.vo.activity.CouponUseQueryVo;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
public interface CouponInfoService extends IService<CouponInfo> {

    Page<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}

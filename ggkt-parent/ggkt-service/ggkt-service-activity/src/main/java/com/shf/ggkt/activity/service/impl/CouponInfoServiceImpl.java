package com.shf.ggkt.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.activity.mapper.CouponInfoMapper;
import com.shf.ggkt.activity.service.CouponInfoService;
import com.shf.ggkt.activity.service.CouponUseService;
import com.shf.ggkt.client.user.UserInfoFeignClient;
import com.shf.ggkt.model.activity.CouponInfo;
import com.shf.ggkt.model.activity.CouponUse;
import com.shf.ggkt.model.user.UserInfo;
import com.shf.ggkt.vo.activity.CouponUseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
    @Autowired
    private CouponUseService couponUseService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    /**
     * 获取已经使用优惠券列表（条件查询分页）
     *
     * @param pageParam
     * @param couponUseQueryVo
     * @return
     */
    @Override
    public Page<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
//        获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();

//        封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id", couponId);
        }
        if (!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status", couponStatus);
        }
        if (!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time", getTimeBegin);
        }
        if (!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time", getTimeEnd);
        }

//        调用方法条件分页查询
        Page<CouponUse> pageModel = couponUseService.page(pageParam, wrapper);
        List<CouponUse> couponUseList = pageModel.getRecords();
        couponUseList.stream().forEach(this::getUserInfoById);

        return pageModel;
    }

    /**
     * 更新优惠券使用状态
     * @param couponUseId
     * @param orderId
     */
    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }

    /**
     * 根据用户id，通过远程调用得到用户信息
     *
     * @param couponUse
     * @return
     */
    private CouponUse getUserInfoById(CouponUse couponUse) {
//        获取用户id
        Long userId = couponUse.getUserId();
        if (!StringUtils.isEmpty(userId)) {
//            远程调用,得到昵称和手机号
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if (userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
        return couponUse;
    }
}

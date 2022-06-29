package com.atguigu.ggkt.activity.api;

import com.atguigu.ggkt.activity.service.CouponInfoService;
import com.atguigu.ggkt.model.activity.CouponInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "优惠券接口")
@RestController
@RequestMapping("/api/activity/couponInfo")
public class CouponInfoApiController {

    @Autowired
    private CouponInfoService couponInfoService;

    //根据优惠券id查询
    @ApiOperation(value = "获取优惠券")
    @GetMapping(value = "inner/getById/{couponId}")
    public CouponInfo getById(@PathVariable("couponId") Long couponId) {
        CouponInfo couponInfo = couponInfoService.getById(couponId);
        return couponInfo;
    }

    //更新优惠券
    @ApiOperation(value = "更新优惠券使用状态")
    @GetMapping(value = "inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(@PathVariable("couponUseId") Long couponUseId, @PathVariable("orderId") Long orderId) {
        couponInfoService.updateCouponInfoUseStatus(couponUseId, orderId);
        return true;
    }
}

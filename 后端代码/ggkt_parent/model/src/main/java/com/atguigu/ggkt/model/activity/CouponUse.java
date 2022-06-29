package com.atguigu.ggkt.model.activity;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "CouponUse")
@TableName("coupon_use")
public class CouponUse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "购物券ID")
	@TableField("coupon_id")
	private Long couponId;

	@ApiModelProperty(value = "用户ID")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "订单ID")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "购物券状态（0：未使用 1：已使用）")
	@TableField("coupon_status")
	private String couponStatus;

	@ApiModelProperty(value = "获取时间")
	@TableField("get_time")
	private Date getTime;

	@ApiModelProperty(value = "使用时间")
	@TableField("using_time")
	private Date usingTime;

	@ApiModelProperty(value = "支付时间")
	@TableField("used_time")
	private Date usedTime;

	@ApiModelProperty(value = "过期时间")
	@TableField("expire_time")
	private Date expireTime;

}
package com.atguigu.ggkt.model.order;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@TableField("phone")
	private String phone;

	@ApiModelProperty(value = "原始金额")
	@TableField("origin_amount")
	private BigDecimal originAmount;

	@ApiModelProperty(value = "优惠券减免")
	@TableField("coupon_reduce")
	private BigDecimal couponReduce;

	@ApiModelProperty(value = "最终金额")
	@TableField("final_amount")
	private BigDecimal finalAmount;

	@ApiModelProperty(value = "订单状态")
	@TableField("order_status")
	private String orderStatus;

	@ApiModelProperty(value = "订单交易编号（第三方支付用)")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "订单描述(第三方支付用)")
	@TableField("trade_body")
	private String tradeBody;

	@ApiModelProperty(value = "session id")
	@TableField("session_id")
	private String sessionId;

	@ApiModelProperty(value = "地区id")
	@TableField("province")
	private String province;

	@ApiModelProperty(value = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("pay_time")
	private Date payTime;

	@ApiModelProperty(value = "失效时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("expire_time")
	private Date expireTime;

}
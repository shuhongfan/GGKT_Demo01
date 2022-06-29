package com.atguigu.ggkt.model.order;

import com.atguigu.ggkt.enums.PaymentStatus;
import com.atguigu.ggkt.enums.PaymentType;
import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "PaymentInfo")
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "对外业务编号")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "订单编号")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "支付宝交易编号")
	@TableField("alipay_trade_no")
	private String alipayTradeNo;

	@ApiModelProperty(value = "支付金额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "交易内容")
	@TableField("trade_body")
	private String tradeBody;

	@ApiModelProperty(value = "paymentType")
	@TableField("payment_type")
	private PaymentType paymentType;

	@ApiModelProperty(value = "支付状态")
	@TableField("payment_status")
	private PaymentStatus paymentStatus;

	@ApiModelProperty(value = "回调信息")
	@TableField("callback_content")
	private String callbackContent;

	@ApiModelProperty(value = "回调时间")
	@TableField("callback_time")
	private Date callbackTime;

}
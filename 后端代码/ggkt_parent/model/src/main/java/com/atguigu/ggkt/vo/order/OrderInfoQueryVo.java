package com.atguigu.ggkt.vo.order;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class OrderInfoQueryVo {
	
	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "nickName")
	private String nickName;

	@ApiModelProperty(value = "phone")
	private String phone;

	@ApiModelProperty(value = "订单状态")
	private Integer orderStatus;

	@ApiModelProperty(value = "订单交易编号（第三方支付用)")
	private String outTradeNo;

	@ApiModelProperty(value = "地区id")
	private String province;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;

	@ApiModelProperty(value = "创建时间")
	private String createTimeEnd;

}


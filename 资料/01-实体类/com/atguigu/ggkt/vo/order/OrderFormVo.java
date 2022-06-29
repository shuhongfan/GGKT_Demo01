package com.atguigu.ggkt.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderFormVo  {

	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "优惠券id")
	private Long couponId;

	@ApiModelProperty(value = "优惠券领取表id")
	private Long couponUseId;
}


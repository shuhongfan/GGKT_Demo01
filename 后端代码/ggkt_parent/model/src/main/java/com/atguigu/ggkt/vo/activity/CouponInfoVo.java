package com.atguigu.ggkt.vo.activity;

import com.atguigu.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CouponInfo")
public class CouponInfoVo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty(value = "优惠券领取表id")
	private Long couponUseId;

	@ApiModelProperty(value = "是否可用")
	private Integer available;

	@ApiModelProperty(value = "优惠卷名字")
	private String name;

	@ApiModelProperty(value = "卡有效开始时间 (时间戳, 单位毫秒)")
	private Long startAt;

	@ApiModelProperty(value = "卡有效结束时间 (时间戳, 单位毫秒)")
	private Long endAt;

	@ApiModelProperty(value = "满减条件")
	private String condition;

	@ApiModelProperty(value = "描述信息，优惠券可用时展示")
	private String description;

	@ApiModelProperty(value = "不可用原因，优惠券不可用时展示")
	private String reason;

	@ApiModelProperty(value = "折扣券优惠金额，单位分")
	private String value;

	@ApiModelProperty(value = "折扣券优惠金额文案")
	private String valueDesc;

	@ApiModelProperty(value = "单位文案")
	private String unitDesc;

}
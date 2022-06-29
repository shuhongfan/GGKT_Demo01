package com.atguigu.ggkt.vo.order;

import com.atguigu.ggkt.model.order.OrderInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderInfoVo extends OrderInfo {

	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "课程名称")
	private String courseName;

	@ApiModelProperty(value = "课程封面图片路径")
	private String cover;

	@ApiModelProperty(value = "总时长:分钟")
	private Integer durationSum;

	@ApiModelProperty(value = "观看进度总时长:分钟")
	private Integer progressSum;

	@ApiModelProperty(value = "观看进度")
	private Integer progress;
}


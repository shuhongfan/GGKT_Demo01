package com.atguigu.ggkt.vo.vod;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class TeacherQueryVo {
	
	@ApiModelProperty(value = "讲师姓名")
	private String name;

	@ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
	private Integer level;

	@ApiModelProperty(value = "入驻时间")
	private String joinDateBegin;

	@ApiModelProperty(value = "入驻时间")
	private String joinDateEnd;


}


package com.atguigu.ggkt.vo.live;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LiveCourseFormVo {
	
	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "直播名称")
	private String courseName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "直播开始时间")
	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "直播结束时间")
	private Date endTime;

	@ApiModelProperty(value = "主播老师id")
	private Long teacherId;

	@ApiModelProperty(value = "主播密码")
	private String password;

	@ApiModelProperty(value = "课程简介")
	private String description;

	@ApiModelProperty(value = "课程封面图片路径")
	private String cover;
}


package com.atguigu.ggkt.model.live;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CourseDescription")
@TableName("live_course_description")
public class LiveCourseDescription extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "课程简介")
	@TableField("description")
	private String description;

}
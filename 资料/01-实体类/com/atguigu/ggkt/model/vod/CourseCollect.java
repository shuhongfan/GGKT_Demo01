package com.atguigu.ggkt.model.vod;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CourseCollect")
@TableName("course_collect")
public class CourseCollect extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程讲师ID")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "会员ID")
	@TableField("user_id")
	private Long userId;

}
package com.atguigu.ggkt.vo.vod;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class CourseQueryVo {
	
	@ApiModelProperty(value = "课程讲师ID")
	private Long teacherId;

	@ApiModelProperty(value = "课程专业ID")
	private Long subjectId;

	@ApiModelProperty(value = "课程专业父级ID")
	private Long subjectParentId;

	@ApiModelProperty(value = "课程标题")
	private String title;

}


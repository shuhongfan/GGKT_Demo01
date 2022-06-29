package com.atguigu.ggkt.model.vod;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "Course")
@TableName("course")
public class Course extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程讲师ID")
	@TableField("teacher_id")
	private Long teacherId;

	@ApiModelProperty(value = "课程专业ID")
	@TableField("subject_id")
	private Long subjectId;

	@ApiModelProperty(value = "课程专业父级ID")
	@TableField("subject_parent_id")
	private Long subjectParentId;

	@ApiModelProperty(value = "课程标题")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
	@TableField("price")
	private BigDecimal price;

	@ApiModelProperty(value = "总课时")
	@TableField("lesson_num")
	private Integer lessonNum;

	@ApiModelProperty(value = "视频总时长（秒）")
	@TableField("duration_sum")
	private Float durationSum;

	@ApiModelProperty(value = "课程封面图片路径")
	@TableField("cover")
	private String cover;

	@ApiModelProperty(value = "销售数量")
	@TableField("buy_count")
	private Long buyCount;

	@ApiModelProperty(value = "浏览数量")
	@TableField("view_count")
	private Long viewCount;

	@ApiModelProperty(value = "课程状态 0未发布 1已发布")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "课程发布时间")
	@TableField("publish_time")
	private Date publishTime;

}
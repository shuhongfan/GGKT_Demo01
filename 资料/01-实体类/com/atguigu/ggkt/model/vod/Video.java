package com.atguigu.ggkt.model.vod;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Video")
@TableName("video")
public class Video extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "章节ID")
	@TableField("chapter_id")
	private Long chapterId;

	@ApiModelProperty(value = "节点名称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "云端视频资源")
	@TableField("video_source_id")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	@TableField("video_original_name")
	private String videoOriginalName;

	@ApiModelProperty(value = "排序字段")
	@TableField("sort")
	private Integer sort;

	@ApiModelProperty(value = "播放次数")
	@TableField("play_count")
	private Long playCount;

	@ApiModelProperty(value = "是否可以试听：0收费 1免费")
	@TableField("is_free")
	private Integer isFree;

	@ApiModelProperty(value = "视频时长（秒）")
	@TableField("duration")
	private Float duration;

	@ApiModelProperty(value = "视频源文件大小（字节）")
	@TableField("size")
	private Long size;

	@ApiModelProperty(value = "乐观锁")
	@TableField("version")
	private Long version;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

}
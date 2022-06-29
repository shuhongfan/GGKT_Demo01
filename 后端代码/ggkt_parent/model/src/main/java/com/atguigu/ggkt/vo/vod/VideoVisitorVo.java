package com.atguigu.ggkt.vo.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVisitorVo {

	@ApiModelProperty(value = "课程id")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "视频id")
	@TableField("video_id")
	private Long videoId;

	@ApiModelProperty(value = "来访者用户id")
	@TableField("user_id")
	private String userId;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "进入时间")
	@TableField("join_time")
	private String joinTime;

	@ApiModelProperty(value = "用户停留的时间(单位：秒)")
	@TableField("duration")
	private Long duration;
}


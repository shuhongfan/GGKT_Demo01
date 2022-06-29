package com.atguigu.ggkt.model.live;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "LiveVisitor")
@TableName("live_visitor")
public class LiveVisitor extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播课程id")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "直播课程")
	@TableField("course_name")
	private String courseName;

	@ApiModelProperty(value = "来访者用户id")
	@TableField("user_id")
	private String userId;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "进入时间")
	@TableField("join_time")
	private String joinTime;

	@ApiModelProperty(value = "离开的时间")
	@TableField("leave_time")
	private String leaveTime;

	@ApiModelProperty(value = "用户地理位置")
	@TableField("location")
	private String location;

	@ApiModelProperty(value = "用户停留的时间(单位：秒)")
	@TableField("duration")
	private Long duration;

	@ApiModelProperty(value = "用户停留时间(时分秒)")
	@TableField("duration_time")
	private String durationTime;

	@ApiModelProperty(value = "平台来访者id，去重使用")
	@TableField("live_visitor_id")
	private String liveVisitorId;

}
package com.atguigu.ggkt.vo.live;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class LiveCourseQueryVo {
	
	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "直播名称")
	private String courseName;

	@ApiModelProperty(value = "直播开始时间")
	private Date startTime;

	@ApiModelProperty(value = "直播结束时间")
	private Date endTime;

	@ApiModelProperty(value = "接入方主播账号或ID或手机号等，最长32位")
	private String account;

	@ApiModelProperty(value = "主播表id")
	private Long liveZhuboId;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "updateTime")
	private Date updateTime;

	@ApiModelProperty(value = "isDeleted")
	private Integer isDeleted;


}


package com.atguigu.ggkt.vo.live;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class LiveVisitorQueryVo {
	
	@ApiModelProperty(value = "直播课程id")
	private Long liveCourseId;

}


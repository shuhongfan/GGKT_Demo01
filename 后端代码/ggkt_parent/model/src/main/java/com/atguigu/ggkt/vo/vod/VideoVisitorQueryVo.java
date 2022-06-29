package com.atguigu.ggkt.vo.vod;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class VideoVisitorQueryVo {
	
	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "视频id")
	private Long videoId;

	@ApiModelProperty(value = "进入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
}


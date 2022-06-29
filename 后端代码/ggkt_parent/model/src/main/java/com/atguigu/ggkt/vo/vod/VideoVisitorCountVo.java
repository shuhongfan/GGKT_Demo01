package com.atguigu.ggkt.vo.vod;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVisitorCountVo {

	@ApiModelProperty(value = "进入时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String joinTime;

	@ApiModelProperty(value = "用户个数")
	private Integer userCount;


}


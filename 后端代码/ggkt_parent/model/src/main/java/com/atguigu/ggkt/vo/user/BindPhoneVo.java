package com.atguigu.ggkt.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BindPhoneVo {
	
	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "验证码")
	private String code;

}


package com.atguigu.ggkt.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoQueryVo {
	
	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "身份证号码")
	private String idNo;

	@ApiModelProperty(value = "性别")
	private String sex;

	@ApiModelProperty(value = "电话号码")
	private String phone;

}


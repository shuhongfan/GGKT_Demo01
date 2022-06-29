package com.atguigu.ggkt.model.user;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "UserInfo")
@TableName("user_info")
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手机号")
	@TableField("phone")
	private String phone;

	@ApiModelProperty(value = "用户密码")
	@TableField("password")
	private String password;

	@ApiModelProperty(value = "用户姓名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "性别")
	@TableField("sex")
	private Integer sex;

	@ApiModelProperty(value = "头像")
	@TableField("avatar")
	private String avatar;

	@ApiModelProperty(value = "省")
	@TableField("province")
	private String province;

	@ApiModelProperty(value = "0：未订阅 1：已订阅")
	@TableField("subscribe")
	private Integer subscribe;

	@ApiModelProperty(value = "小程序open id")
	@TableField("open_id")
	private String openId;

	@ApiModelProperty(value = "微信开放平台unionID")
	@TableField("union_id")
	private String unionId;

	@ApiModelProperty(value = "推荐人用户id")
	@TableField("recommend_id")
	private Long recommendId;

	@ApiModelProperty(value = "status")
	@TableField("status")
	private Integer status;

}
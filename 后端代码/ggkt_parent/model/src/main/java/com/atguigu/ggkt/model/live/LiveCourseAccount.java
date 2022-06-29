package com.atguigu.ggkt.model.live;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "LiveCourseAccount")
@TableName("live_course_account")
public class LiveCourseAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播课程id")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "主播登录账号")
	@TableField("zhubo_account")
	private String zhuboAccount;

	@ApiModelProperty(value = "主播登录密码")
	@TableField("zhubo_password")
	private String zhuboPassword;

	@ApiModelProperty(value = "主播登录秘钥")
	@TableField("zhubo_key")
	private String zhuboKey;

	@ApiModelProperty(value = "助教登录秘钥")
	@TableField("admin_key")
	private String adminKey;

	@ApiModelProperty(value = "学生登录秘钥")
	@TableField("user_key")
	private String userKey;

}
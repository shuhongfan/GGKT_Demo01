package com.atguigu.ggkt.model.vod;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "Teacher")
@TableName("teacher")
public class Teacher extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "讲师姓名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "讲师简介")
	@TableField("intro")
	private String intro;

	@ApiModelProperty(value = "讲师资历,一句话说明讲师")
	@TableField("career")
	private String career;

	@ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
	@TableField("level")
	private Integer level;

	@ApiModelProperty(value = "讲师头像")
	@TableField("avatar")
	private String avatar;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

	@ApiModelProperty(value = "入驻时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("join_date")
	private Date joinDate;

}
package com.atguigu.ggkt.model.live;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "LiveCourseConfig")
@TableName("live_course_config")
public class LiveCourseConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播课程id")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "界面模式 1全屏模式 0二分屏 2课件模式")
	@TableField("page_view_mode")
	private Integer pageViewMode;

	@ApiModelProperty(value = "是否开启 观看人数 0否 1是")
	@TableField("number_enable")
	private Integer numberEnable;

	@ApiModelProperty(value = "商城是否开启 0未开启 1开启")
	@TableField("store_enable")
	private Integer storeEnable;

	@ApiModelProperty(value = "1商品列表,2商城链接,3商城二维码")
	@TableField("store_type")
	private Integer storeType;

}
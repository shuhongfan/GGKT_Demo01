package com.atguigu.ggkt.model.live;

import com.atguigu.ggkt.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "LiveCourseGoods")
@TableName("live_course_goods")
public class LiveCourseGoods extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播课程id")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "推荐点播课程id")
	@TableField("goods_id")
	private Long goodsId;

	@ApiModelProperty(value = "商品名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "图片")
	@TableField("img")
	private String img;

	@ApiModelProperty(value = "商品现价")
	@TableField("price")
	private String price;

	@ApiModelProperty(value = "商品原价")
	@TableField("originalPrice")
	private String originalPrice;

	@ApiModelProperty(value = "商品标签")
	@TableField("tab")
	private Integer tab;

	@ApiModelProperty(value = "商品链接")
	@TableField("url")
	private String url;

	@ApiModelProperty(value = "商品状态：0下架，1上架，2推荐")
	@TableField("putaway")
	private String putaway;

	@ApiModelProperty(value = "购买模式(1,链接购买 2,二维码购买)")
	@TableField("pay")
	private Integer pay;

	@ApiModelProperty(value = "商品二维码")
	@TableField("qrcode")
	private String qrcode;

}
package com.atguigu.ggkt.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author helen
 * @since 2020/6/6
 */
@ApiModel("课程基本信息")
@Data
public class CourseFormVo {

    @ApiModelProperty(value = "课程ID")
    private Long id;

    @ApiModelProperty(value = "课程讲师ID")
    private Long teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private Long subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private Long subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}

package com.atguigu.ggkt.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author helen
 * @since 2020/6/6
 */
@ApiModel("课程对象")
@Data
public class CourseVo {

    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "一级分类标题")
    private String subjectParentTitle;
    @ApiModelProperty(value = "二级分类标题")
    private String subjectTitle;
    @ApiModelProperty(value = "讲师id")
    private Long teacherId;
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程销售价格")
    private String price;//只用于显示
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "销售数量")
    private Long buyCount;
    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;
    @ApiModelProperty(value = "课程状态")
    private String status;
    @ApiModelProperty(value = "课程发布时间")
    private String publishTime;
}

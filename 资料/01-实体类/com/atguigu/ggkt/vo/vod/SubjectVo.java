package com.atguigu.ggkt.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author helen
 * @since 2020/6/5
 */
@ApiModel("课程分类列表")
@Data
public class SubjectVo {

    @ApiModelProperty(value = "课程分类ID")
    private Long id;

    @ApiModelProperty(value = "课程分类名称")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "课程二级分类列表")
    private List<SubjectVo> children = new ArrayList<>();
}

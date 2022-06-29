package com.atguigu.ggkt.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author helen
 * @since 2020/6/6
 */
@ApiModel("课程章节对象")
@Data
public class ChapterVo {

    @ApiModelProperty(value = "章节ID")
    private Long id;
    @ApiModelProperty(value = "章节标题")
    private String title;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "章节下的课时列表")
    private List<VideoVo> children = new ArrayList<>();
}
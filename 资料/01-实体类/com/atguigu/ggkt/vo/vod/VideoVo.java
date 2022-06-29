package com.atguigu.ggkt.vo.vod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author helen
 * @since 2020/6/6
 */
@ApiModel("课时信息")
@Data
public class VideoVo {
    @ApiModelProperty(value = "课时ID")
    private Long id;
    @ApiModelProperty(value = "课时标题")
    private String title;
    @ApiModelProperty(value = "是否可以试听")
    private Integer isFree;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "阿里云视频ID")
    private String videoSourceId;
}

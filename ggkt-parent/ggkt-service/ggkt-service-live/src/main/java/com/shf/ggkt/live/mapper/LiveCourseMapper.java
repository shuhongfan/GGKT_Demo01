package com.shf.ggkt.live.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.ggkt.model.live.LiveCourse;
import com.shf.ggkt.vo.live.LiveCourseVo;

import java.util.List;

/**
 * <p>
 * 直播课程表 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    List<LiveCourseVo> getLatelyList();
}

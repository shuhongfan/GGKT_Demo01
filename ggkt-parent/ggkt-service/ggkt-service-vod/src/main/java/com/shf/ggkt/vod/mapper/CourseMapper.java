package com.shf.ggkt.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.ggkt.model.vod.Course;
import com.shf.ggkt.vo.vod.CoursePublishVo;
import com.shf.ggkt.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * id查询发布课程信息
     * @param id
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(Long id);

    /**
     * 根据课程id查询课程详情数据
     * @param courseId
     * @return
     */
    CourseVo selectCourseVoById(Long courseId);
}

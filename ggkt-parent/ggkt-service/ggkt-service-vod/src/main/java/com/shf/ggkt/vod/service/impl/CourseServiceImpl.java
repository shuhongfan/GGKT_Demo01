package com.shf.ggkt.vod.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.model.vod.Course;
import com.shf.ggkt.model.vod.CourseDescription;
import com.shf.ggkt.model.vod.Subject;
import com.shf.ggkt.model.vod.Teacher;
import com.shf.ggkt.vo.vod.*;
import com.shf.ggkt.vod.mapper.CourseMapper;
import com.shf.ggkt.vod.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 点播课程列表，课程查询带分页
     *
     * @param pageParam
     * @param courseQueryVo
     * @return
     */
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
//        获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId(); // 二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId(); // 一层分类
        Long teacherId = courseQueryVo.getTeacherId();

//        判断条件为空，封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.like("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.like("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.like("teacher_id", teacherId);
        }

//        调用方法实现条件查询
        Page<Course> coursePage = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = coursePage.getTotal();
        long totalPage = coursePage.getPages();
        List<Course> list = pageParam.getRecords();

//        查询数据的id，获取这些id对应的名称，进行封装，最终显示  讲师id  课程分类id（一层、二层）
        list.stream().forEach(item -> {
            this.getNameById(item);
        });


//        封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", list);
        return map;
    }

    /**
     * 添加课程基本信息
     *
     * @param courseFormVo
     * @return
     */
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
//        添加课程基本信息，操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

//        添加课程描述信息，操作course_description
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
//        设置课程id
        courseDescription.setId(course.getId());
        courseDescription.setCourseId(course.getId());
        descriptionService.save(courseDescription);

        return course.getId();
    }

    /**
     * 根据id获取课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
//        课程基本信息
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

//        课程描述信息
        CourseDescription courseDescription = descriptionService.getById(course);
//        封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);

//        封装描述
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    /**
     * 修改课程信息
     *
     * @param courseFormVo
     */
    @Override
    public void updateCourseById(CourseFormVo courseFormVo) {
//        修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

//        修改课程描述信息
        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
//        设置课程描述id
        description.setId(course.getId());
        descriptionService.updateById(description);
    }

    /**
     * id查询发布课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 课程最终发布
     *
     * @param id
     */
    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1); // 课程已经发布
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

    /**
     * 删除课程
     *
     * @param id
     */
    @Override
    public void removeCourseId(Long id) {
//        根据课程id删除小节
        videoService.removeVideoByCourseId(id);

//        根据课程id删除章节
        chapterService.removeChapterByCourseId(id);

//        根据课程id删除课程描述
        descriptionService.removeById(id);

//        根据课程id删除课程
        baseMapper.deleteById(id);

    }


    /**
     * 根据课程分类查询课程列表（分页）
     *
     * @param pageParam
     * @param courseQueryVo
     * @return
     */
    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
//        获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();

//        判断条件值是否为空，封装
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

//        调用方法进行条件分页查询
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数

//        每页数据集合
        List<Course> records = pages.getRecords();

//        封装其他数据（获取讲师名称和课程分类名称）
        records.stream().forEach(item -> {
            this.getTeacherAndSubjectName(item);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);

        return map;
    }

    /**
     * 封装其他数据（获取讲师名称和课程分类名称）
     *
     * @param course
     */
    private Course getTeacherAndSubjectName(Course course) {
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        if (teacherId != null) {
            course.getParam().put("teacherName", teacher.getName());
        }
//        课程分类名称
        Long subjectParentId = course.getSubjectParentId();
        Subject oneSubject = subjectService.getById(subjectParentId);
        if (oneSubject != null) {
            course.getParam().put("subjectParentTitle", oneSubject.getTitle());
        }

        Long subjectId = course.getSubjectId();
        Subject twoSubject = subjectService.getById(subjectId);
        if (twoSubject != null) {
            course.getParam().put("subjectTitle", twoSubject.getTitle());
        }
        return course;
    }

    /**
     * 根据ID查询课程
     *
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getInfoById(Long courseId) {
//        view_count流量数量+1
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

//        根据课程id查询课程详情数据
        CourseVo courseVo = baseMapper.selectCourseVoById(courseId);

//        课程章节数据
        List<ChapterVo> chapterVoList = chapterService.getNestedTreeList(courseId);

//        课程描述信息
        CourseDescription courseDescription = descriptionService.getById(courseId);

//        课程所属讲师信息
        Teacher teacher = teacherService.getById(course.getTeacherId());

//        封装map集合，返回
        Map<String, Object> map = new HashMap<>();
        Boolean isBuy = false;

        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ?
                courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", isBuy);//是否购买
        return map;
    }

    /**
     * 查询所有课程
     * @return
     */
    @Override
    public List<Course> findList() {
        List<Course> list = baseMapper.selectList(null);
        list.stream().forEach(this::getTeacherAndSubjectName);
        return list;
    }

    /**
     * 获取这些id对应名称，进行封装，最终显示
     *
     * @param course
     */
    private Course getNameById(Course course) {
//        根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }

//        根据课程分类id获取课程一层分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (subjectOne != null) {
            course.getParam().put("subjectParentTitle", subjectOne.getTitle());
        }

//        根据课程分类id获取课程二层分类名称
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if (subjectTwo != null) {
            course.getParam().put("subjectTitle", subjectTwo.getTitle());
        }

        return course;
    }
}

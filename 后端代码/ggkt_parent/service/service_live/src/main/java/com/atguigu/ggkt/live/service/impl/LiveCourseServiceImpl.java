package com.atguigu.ggkt.live.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.client.course.CourseFeignClient;
import com.atguigu.ggkt.client.user.UserInfoFeignClient;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.live.mtcloud.CommonResult;
import com.atguigu.ggkt.live.mtcloud.MTCloud;
import com.atguigu.ggkt.live.service.*;
import com.atguigu.ggkt.model.live.*;
import com.atguigu.ggkt.live.mapper.LiveCourseMapper;
import com.atguigu.ggkt.model.user.UserInfo;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.utils.DateUtil;
import com.atguigu.ggkt.vo.live.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播课程表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-09
 */
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements LiveCourseService {

    @Autowired
    private CourseFeignClient teacherFeignClient;

    @Autowired
    private LiveCourseDescriptionService liveCourseDescriptionService;

    @Autowired
    private LiveCourseAccountService liveCourseAccountService;

    @Autowired
    private LiveCourseConfigService liveCourseConfigService;

    @Autowired
    private LiveCourseGoodsService liveCourseGoodsService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    @Autowired
    private MTCloud mtCloudClient;

    //直播课程列表
    @Override
    public IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam) {
        //分页查询
        IPage<LiveCourse> pageModel =
                baseMapper.selectPage(pageParam, null);
        //获取课程讲师信息
        List<LiveCourse> LiveCourseList = pageModel.getRecords();
        //遍历获取直播课程list集合
        for (LiveCourse liveCourse:LiveCourseList) {
            //每个课程讲师id
            Long teacherId = liveCourse.getTeacherId();
            //根据讲师id查询讲师信息
            Teacher teacher = teacherFeignClient.getTeacherInfo(teacherId);
            //进行封装
            if(teacher != null) {
                liveCourse.getParam().put("teacherName", teacher.getName());
                liveCourse.getParam().put("teacherLevel", teacher.getLevel());
            }

            liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy年MM月dd HH:mm"));
            liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy年MM月dd HH:mm"));
        }
        return pageModel;
    }

    //直播课程添加
    @Override
    public void saveLive(LiveCourseFormVo liveCourseFormVo) {
        //LiveCourseFormVo -- LiveCourse
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo,liveCourse);
        //获取讲师信息
        Teacher teacher =
                teacherFeignClient.getTeacherInfo(liveCourseFormVo.getTeacherId());
        //调用方法添加直播课程
        //创建map集合，封装直播课程其他参数
        HashMap<Object, Object> options = new HashMap<>();
        options.put("scenes", 2);//直播类型。1: 教育直播，2: 生活直播。默认 1，说明：根据平台开通的直播类型填写
        options.put("password", liveCourseFormVo.getPassword());
//       course_name 课程名称
//       account 发起直播课程的主播账号
//       start_time 课程开始时间,格式: 2015-01-10 12:00:00
//       end_time 课程结束时间,格式: 2015-01-10 13:00:00
//         nickname  昵称
//         accountIntro 主播介绍
//         options 其他参数
        try {
            String res = mtCloudClient.courseAdd(liveCourse.getCourseName(),
                    teacher.getId().toString(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options);
            System.out.println("res:"+res);
            //把创建之后返回结果判断
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) { //成功
                //添加直播基本信息
                JSONObject object = commonResult.getData();
                Long course_id = object.getLong("course_id");//直播课程id
                liveCourse.setCourseId(course_id);
                baseMapper.insert(liveCourse);

                //添加直播描述信息
                LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescription.setLiveCourseId(liveCourse.getId());
                liveCourseDescriptionService.save(liveCourseDescription);

                //添加直播账号信息
                LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
                liveCourseAccount.setLiveCourseId(liveCourse.getId());
                liveCourseAccount.setZhuboAccount(object.getString("bid"));
                liveCourseAccount.setZhuboPassword(liveCourseFormVo.getPassword());
                liveCourseAccount.setAdminKey(object.getString("admin_key"));
                liveCourseAccount.setUserKey(object.getString("user_key"));
                liveCourseAccount.setZhuboKey(object.getString("zhubo_key"));
                liveCourseAccountService.save(liveCourseAccount);
            } else {
                System.out.println(commonResult.getmsg());
                throw new GgktException(20001,"直播创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //删除直播课程
    @Override
    public void removeLive(Long id) {
        //根据id查询直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        if(liveCourse != null) {
            //获取直播courseid
            Long courseId = liveCourse.getCourseId();
            try {
                //调用方法删除平台直播课程
                mtCloudClient.courseDelete(courseId.toString());
                //删除表数据
                baseMapper.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GgktException(20001,"删除直播课程失败");
            }
        }
    }

    //id查询直播课程基本信息和描述信息
    @Override
    public LiveCourseFormVo getLiveCourseVo(Long id) {
        //获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        //获取直播课程描述信息
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);
        //封装
        LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        liveCourseFormVo.setDescription(liveCourseDescription.getDescription());

        return liveCourseFormVo;
    }

    //更新
    @Override
    public void updateLiveById(LiveCourseFormVo liveCourseFormVo) {
        //根据id获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(liveCourseFormVo.getId());
        BeanUtils.copyProperties(liveCourseFormVo,liveCourse);
        //讲师
        Teacher teacher =
                teacherFeignClient.getTeacherInfo(liveCourseFormVo.getTeacherId());

    //             *   course_id 课程ID
    //     *   account 发起直播课程的主播账号
    //     *   course_name 课程名称
    //     *   start_time 课程开始时间,格式:2015-01-01 12:00:00
    //                *   end_time 课程结束时间,格式:2015-01-01 13:00:00
    //                *   nickname 	主播的昵称
    //                *   accountIntro 	主播的简介
    //                *  options 		可选参数
        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloudClient.courseUpdate(liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options);
            //返回结果转换，判断是否成功
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                //更新直播课程基本信息
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.updateById(liveCourse);
                //直播课程描述信息更新
                LiveCourseDescription liveCourseDescription =
                        liveCourseDescriptionService.getLiveCourseById(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);
            } else {
                throw new GgktException(20001,"修改直播课程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取直播配置信息
    @Override
    public LiveCourseConfigVo getCourseConfig(Long id) {
        //根据课程id查询配置信息
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getCourseConfigCourseId(id);
        //封装LiveCourseConfigVo
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        if(liveCourseConfig != null) {
            //查询直播课程商品列表
            List<LiveCourseGoods> liveCourseGoodslist = liveCourseGoodsService.getGoodsListCourseId(id);
            //封装到liveCourseConfigVo里面
            BeanUtils.copyProperties(liveCourseConfig,liveCourseConfigVo);
            //封装商品列表
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodslist);
        }
        return liveCourseConfigVo;
    }

    //修改配置
    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {
        //1 修改直播配置表
        LiveCourseConfig liveCourseConfig = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo,liveCourseConfig);
        if(liveCourseConfigVo.getId() == null) {
            liveCourseConfigService.save(liveCourseConfig);
        } else {
            liveCourseConfigService.updateById(liveCourseConfig);
        }

        //2 修改直播商品表
        //根据课程id删除直播商品列表
        LambdaQueryWrapper<LiveCourseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseGoods::getLiveCourseId,liveCourseConfigVo.getLiveCourseId());
        liveCourseGoodsService.remove(wrapper);
        //添加商品列表
        if(!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())) {
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }

        //3 修改在直播平台
        this.updateLifeConfig(liveCourseConfigVo);
    }

    //获取最近的直播
    @Override
    public List<LiveCourseVo> getLatelyList() {
        List<LiveCourseVo> liveCourseVoList = baseMapper.getLatelyList();
        for (LiveCourseVo liveCourseVo:liveCourseVoList) {
            //封装开始和结束时间
            liveCourseVo.setStartTimeString(new DateTime(liveCourseVo.getStartTime()).toString("yyyy年MM月dd HH:mm"));
            liveCourseVo.setEndTimeString(new DateTime(liveCourseVo.getEndTime()).toString("HH:mm"));
            //封装讲师
            Long teacherId = liveCourseVo.getTeacherId();
            Teacher teacher = teacherFeignClient.getTeacherInfo(teacherId);
            liveCourseVo.setTeacher(teacher);

            //封装直播状态
            liveCourseVo.setLiveStatus(this.getLiveStatus(liveCourseVo));
        }
        return liveCourseVoList;
    }

    //获取用户access_token
    @Override
    public JSONObject getAccessToken(Long id, Long userId) {
        //根据课程id获取直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        //根据用户id获取用户信息
        UserInfo userInfo = userInfoFeignClient.getById(userId);

        //封装需要参数
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        /**
         *  course_id      课程ID
         *  uid            用户唯一ID
         *  nickname       用户昵称
         *  role           用户角色，枚举见:ROLE 定义
         *     expire         有效期,默认:3600(单位:秒)
         *   options        可选项，包括:gender:枚举见上面GENDER定义,avatar:头像地址
         */
        try {
            String res = mtCloudClient.courseAccess(liveCourse.getCourseId().toString(),
                    userId.toString(),
                    userInfo.getNickName(),
                    MTCloud.ROLE_USER,
                    3600,
                    options);
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                System.out.println("access::"+object.getString("access_token"));
                return object;
            } else {
                throw new GgktException(20001,"获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据ID查询课程
    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        LiveCourse liveCourse = this.getById(courseId);
        liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy年MM月dd HH:mm"));
        liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy年MM月dd HH:mm"));
        Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourse.getTeacherId());
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("liveCourse", liveCourse);
        map.put("liveStatus", this.getLiveStatus(liveCourse));
        map.put("teacher", teacher);
        if(null != liveCourseDescription) {
            map.put("description", liveCourseDescription.getDescription());
        } else {
            map.put("description", "");
        }
        return map;
    }

    /**
     * 直播状态 0：未开始 1：直播中 2：直播结束
     * @param liveCourse
     * @return
     */
    private int getLiveStatus(LiveCourse liveCourse) {
        // 直播状态 0：未开始 1：直播中 2：直播结束
        int liveStatus = 0;
        Date curTime = new Date();
        if(DateUtil.dateCompare(curTime, liveCourse.getStartTime())) {
            liveStatus = 0;
        } else if(DateUtil.dateCompare(curTime, liveCourse.getEndTime())) {
            liveStatus = 1;
        } else {
            liveStatus = 2;
        }
        return liveStatus;
    }

    //修改在直播平台
    private void updateLifeConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourse liveCourse =
                baseMapper.selectById(liveCourseConfigVo.getLiveCourseId());
        //封装平台方法需要参数
        //参数设置
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        //界面模式
        options.put("pageViewMode", liveCourseConfigVo.getPageViewMode());
        //观看人数开关
        JSONObject number = new JSONObject();
        number.put("enable", liveCourseConfigVo.getNumberEnable());
        options.put("number", number.toJSONString());
        //观看人数开关
        JSONObject store = new JSONObject();
        number.put("enable", liveCourseConfigVo.getStoreEnable());
        number.put("type", liveCourseConfigVo.getStoreType());
        options.put("store", number.toJSONString());
        //商城列表
        List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if(!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            List<LiveCourseGoodsView> liveCourseGoodsViewList = new ArrayList<>();
            for(LiveCourseGoods liveCourseGoods : liveCourseGoodsList) {
                LiveCourseGoodsView liveCourseGoodsView = new LiveCourseGoodsView();
                BeanUtils.copyProperties(liveCourseGoods, liveCourseGoodsView);
                liveCourseGoodsViewList.add(liveCourseGoodsView);
            }
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status", "0");
            options.put("goodsListEdit ", goodsListEdit.toJSONString());
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }
        try {
            String res = mtCloudClient.courseUpdateLifeConfig(liveCourse.getCourseId().toString(), options);
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
                throw new GgktException(20001,"修改配置信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.shf.ggkt.live.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.client.course.TeacherFeignClient;
import com.shf.ggkt.client.user.UserInfoFeignClient;
import com.shf.ggkt.exception.GgktException;
import com.shf.ggkt.live.mapper.LiveCourseMapper;
import com.shf.ggkt.live.mtcloud.CommonResult;
import com.shf.ggkt.live.mtcloud.MTCloud;
import com.shf.ggkt.live.service.*;
import com.shf.ggkt.model.live.*;
import com.shf.ggkt.model.user.UserInfo;
import com.shf.ggkt.model.vod.Teacher;
import com.shf.ggkt.utils.DateUtil;
import com.shf.ggkt.vo.live.LiveCourseConfigVo;
import com.shf.ggkt.vo.live.LiveCourseFormVo;
import com.shf.ggkt.vo.live.LiveCourseGoodsView;
import com.shf.ggkt.vo.live.LiveCourseVo;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 直播课程表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements LiveCourseService {
    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @Autowired
    private MTCloud mtCloud;

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

    /**
     * 直播课程列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public Page<LiveCourse> selectPage(Page<LiveCourse> pageParam) {
//        分页查询
        Page<LiveCourse> pageModel = baseMapper.selectPage(pageParam, null);
//        获取课程讲师信息
        List<LiveCourse> liveCourses = pageModel.getRecords();
//        遍历获取直播课程list集合
        for (LiveCourse course : liveCourses) {
//            每个课程讲师id
            Long teacherId = course.getTeacherId();
//            根据讲师id查询讲师信息
            Teacher teacher = teacherFeignClient.getTeacherInfo(teacherId);
//            进行封装
            if (teacher != null) {
                course.getParam().put("teacherName", teacher.getName());
                course.getParam().put("teacherLevel", teacher.getLevel());
            }
        }
        return pageModel;
    }

    /**
     * 直播课程添加
     *
     * @param liveCourseFormVo
     */
    @SneakyThrows
    @Override
    public Boolean saveLive(LiveCourseFormVo liveCourseFormVo) {
//        LiveCourseFormVo -->LiveCourseForm
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);

//        获取讲师信息
        Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourseFormVo.getTeacherId());

//        调用方法添加直播课程
//        创建map集合，封装直播课程其他参数
        HashMap<Object, Object> options = new HashMap<>();
        options.put("scenes", 2);//直播类型。1: 教育直播，2: 生活直播。默认 1，说明：根据平台开通的直播类型填写
        options.put("password", liveCourseFormVo.getPassword());

        String res = mtCloud.courseAdd(
                liveCourse.getCourseName(),
                teacher.getId().toString(),
                new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                teacher.getName(),
                teacher.getIntro(),
                options
        );
        System.out.println("return:: " + res);

        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if (Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
//            直播创建成功，添加直播基本信息
            JSONObject object = commonResult.getData();
            liveCourse.setCourseId(object.getLong("course_id")); // 直播课程id
            baseMapper.insert(liveCourse);

            //保存课程详情信息
            LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
            liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
            liveCourseDescription.setLiveCourseId(liveCourse.getId());
            liveCourseDescriptionService.save(liveCourseDescription);

            //保存课程账号信息
            LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
            liveCourseAccount.setLiveCourseId(liveCourse.getId());
            liveCourseAccount.setZhuboAccount(object.getString("bid"));
            liveCourseAccount.setZhuboPassword(liveCourseFormVo.getPassword());
            liveCourseAccount.setAdminKey(object.getString("admin_key"));
            liveCourseAccount.setUserKey(object.getString("user_key"));
            liveCourseAccount.setZhuboKey(object.getString("zhubo_key"));
            liveCourseAccountService.save(liveCourseAccount);
        } else {
            String getmsg = commonResult.getmsg();
            throw new GgktException(20001, getmsg);
        }
        return true;

    }

    /**
     * 删除直播课程
     * @param id
     */

    @Override
    public void removeLive(Long id) {
//        根据id查询直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
        if (liveCourse != null) {
//            获取直播courseId
            Long courseId = liveCourse.getCourseId();
            try {
//            调用方法删除平台直播课程
                mtCloud.courseDelete(courseId.toString());
//                删除表数据
                baseMapper.deleteById(id);
            } catch (Exception e) {
                throw new GgktException(20001, "删除直播课程失败");
            }
        }
    }

    /**
     * 根据id查询直播课程基本信息和描述信息
     * @param id
     * @return
     */
    @Override
    public LiveCourseFormVo getLiveCourseFormVo(Long id) {
//        获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(id);
//        获取直播课程描述信息
        LiveCourseDescription liveCourseDescription =
                liveCourseDescriptionService.getLiveCourseById(id);
//        封装
        LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        liveCourseFormVo.setDescription(liveCourseDescription.getDescription());
        return liveCourseFormVo;
    }

    /**
     * 更新直播课程方法
     * @param liveCourseVo
     */
    @Override
    public void updateLiveById(LiveCourseFormVo liveCourseVo) {
//        根据id获取直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(liveCourseVo.getId());
        BeanUtils.copyProperties(liveCourseVo, liveCourse);
//        讲师
        Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourseVo.getTeacherId());

        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloud.courseUpdate(liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options);
//            返回结果转换，判断是否成功
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if (Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
//                更新直播课程基本信息
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.updateById(liveCourse);
//                更新直播课程描述信息
                LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);
            }
        } catch (Exception e) {
            throw new GgktException(20001, "修改直播课程失败");
        }
    }

    /**
     * 获取直播配置
     * @param id
     * @return
     */
    @Override
    public LiveCourseConfigVo getCourseConfig(Long id) {
//        根据课程id查询配置信息
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getCourseConfigCourseId(id);
//        封装LiveCourseConfigVo
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        if (liveCourseConfig != null) {
//            查询直播课程商品列表
            List<LiveCourseGoods> liveCourseGoodsList = liveCourseGoodsService.getGoodsListCourseId(id);
//            封装到liveCourseConfigVo里面
            BeanUtils.copyProperties(liveCourseConfig, liveCourseConfigVo);
//            封装商品列表
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    /**
     * 修改直播配置
     * @param liveCourseConfigVo
     */
    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {
//        1. 修改直播配置表
        LiveCourseConfig liveCourseConfig = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo, liveCourseConfig);
        if (liveCourseConfigVo.getId() == null) {
            liveCourseConfigService.save(liveCourseConfig); // 添加
        } else {
            liveCourseConfigService.updateById(liveCourseConfig); // 更新
        }

//        2. 修改直播商品表
//        2.1 根据课程id删除直播商品列表
        LambdaQueryWrapper<LiveCourseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseGoods::getLiveCourseId, liveCourseConfigVo.getLiveCourseId());
        liveCourseGoodsService.remove(wrapper);
//        添加添加商品列表
        if (!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())) {
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }

//        3.修改直播平台
        this.updateLifeConfig(liveCourseConfigVo);
    }

    /**
     * 获取最近的直播
     * @return
     */
    @Override
    public List<LiveCourseVo> findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = baseMapper.getLatelyList();
        for (LiveCourseVo liveCourseVo : liveCourseVoList) {
//            封装开始和结束时间
            liveCourseVo.setStartTimeString(new DateTime(liveCourseVo.getStartTime()).toString("yyyy年MM月dd HH:mm"));
            liveCourseVo.setEndTimeString(new DateTime(liveCourseVo.getEndTime()).toString("HH:mm"));
//            封装讲师
            Long teacherId = liveCourseVo.getTeacherId();
            Teacher teacher = teacherFeignClient.getTeacherInfo(teacherId);
            liveCourseVo.setTeacher(teacher);

//            封装直播状态
            liveCourseVo.setLiveStatus(this.getLiveStatus(liveCourseVo));
        }
        return liveCourseVoList;
    }

    /**
     * 获取用户access_token
     * @param id
     * @param userId
     * @return
     */
    @SneakyThrows
    @Override
    public JSONObject getAccessToken(Long id, Long userId) {
//        根据课程id获取直播课程信息
        LiveCourse liveCourse = baseMapper.selectById(id);
//        根据用户id获取用户信息
        UserInfo userInfo = userInfoFeignClient.getById(userId);

//        封装需要的参数
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        String res = mtCloud.courseAccess(liveCourse.getCourseId().toString(),
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
    }

    /**
     * 根据ID查询课程
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getInfoById(Long id) {
        LiveCourse liveCourse = this.getById(id);
        liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy年MM月dd HH:mm"));
        liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy年MM月dd HH:mm"));
        Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourse.getTeacherId());
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);

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

    /**
     * 修改在直播平台
     *
     * @param liveCourseConfigVo
     */
    @SneakyThrows
    private void updateLifeConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourse liveCourse = baseMapper.selectById(liveCourseConfigVo.getLiveCourseId());

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

        String res = mtCloud.courseUpdateConfig(liveCourse.getCourseId().toString(), options);

        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
            throw new GgktException(20001,"修改配置信息失败");
        }
    }
}

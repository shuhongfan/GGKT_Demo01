package com.shf.ggkt.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.client.activity.CouponInfoFeignClient;
import com.shf.ggkt.client.course.CourseFeignClient;
import com.shf.ggkt.client.user.UserInfoFeignClient;
import com.shf.ggkt.exception.GgktException;
import com.shf.ggkt.model.activity.CouponInfo;
import com.shf.ggkt.model.order.OrderDetail;
import com.shf.ggkt.model.order.OrderInfo;
import com.shf.ggkt.model.user.UserInfo;
import com.shf.ggkt.model.vod.Course;
import com.shf.ggkt.order.mapper.OrderInfoMapper;
import com.shf.ggkt.order.service.OrderDetailService;
import com.shf.ggkt.order.service.OrderInfoService;
import com.shf.ggkt.utils.AuthContextHolder;
import com.shf.ggkt.utils.OrderNoUtils;
import com.shf.ggkt.vo.order.OrderFormVo;
import com.shf.ggkt.vo.order.OrderInfoQueryVo;
import com.shf.ggkt.vo.order.OrderInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private CouponInfoFeignClient couponInfoFeignClient;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    /**
     * 订单列表
     *
     * @param pageParam
     * @param orderInfoQueryVo
     * @return
     */
    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
//        获取查询条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

//        判断条件值是否为空，不为空，进行条件封装
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id", userId);
        }
        if (!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq("out_trade_no", outTradeNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }
        if (!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status", orderStatus);
        }

//        调用实现条件分页查询
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long pagesCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();

//        订单里面包含详情内容，封装详情数据，根据订单id查询详情
        records.stream().forEach(this::getOrderDetail);

//        所有需要数据封装map集合，最终返回
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pagesCount);
        map.put("records", records);
        return map;
    }

    /**
     * 新增点播课程订单
     *
     * @param orderFormVo
     * @return
     */
    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
//        1.获取生成订单条件值
        Long couponId = orderFormVo.getCouponId();
        Long courseId = orderFormVo.getCourseId();
        Long userId = AuthContextHolder.getUserId();

//        2. 判断当前用户是否已经生成订单
//        课程id，用户id
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getCourseId, courseId);
        wrapper.eq(OrderDetail::getUserId, userId);
        OrderDetail orderDetailExist = orderDetailService.getOne(wrapper);
        if (orderDetailExist != null) {
            return orderDetailExist.getId(); // 订单已经存在，直接放回订单id
        }


//        3  根据课程id查询课程信息
        Course course = courseFeignClient.getById(courseId);
        if (course == null) {
            throw new GgktException(20001, "课程不存在");
        }

//        4. 根据用户id查询用户信息
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        if (userInfo == null) {
            throw new GgktException(20001, "用户不存在");
        }

//        5. 根据优惠券id查询优惠券信息
        BigDecimal couponReduce = new BigDecimal(0);
        if (couponId != null) {
            CouponInfo couponInfo = couponInfoFeignClient.getById(couponId);
            couponReduce = couponInfo.getAmount();
        }

//        6. 封装订单生成需要数据到对象，完成添加订单
//        6.1 封装数据到OrderInfo对象里面，添加订单基本信息表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(couponReduce);
        orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        this.save(orderInfo);

//        6.2 封装数据到OrderDetail对象里面，添加订单详情信息表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(new BigDecimal(0));
        orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);

//        7. 更新优惠券状态
        if (null != orderFormVo.getCouponUseId()) {
            couponInfoFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
        }

//        8. 返回订单id
        return orderInfo.getId();
    }

    /**
     * 订单id获取订单信息
     *
     * @param id
     * @return
     */
    @Override
    public OrderInfoVo getOrderInfoVoById(Long id) {
//        id查询订单基本信息和详情信息
        OrderInfo orderInfo = baseMapper.selectById(id);
        OrderDetail orderDetail = orderDetailService.getById(id);

//        封装OrderInfoVo
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfoVo.setCourseId(orderDetail.getCourseId());
        orderInfoVo.setCourseName(orderDetail.getCourseName());
        return orderInfoVo;
    }

    /**
     * 更新订单状态：已支付
     * @param out_trade_no
     */
    @Override
    public void updateOrderStatus(String out_trade_no) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOutTradeNo, out_trade_no);
        OrderInfo orderInfo = baseMapper.selectOne(wrapper);

//        设置订单状态
        orderInfo.setOrderStatus("1");

//        调用方法更新
        baseMapper.updateById(orderInfo);
    }

    /**
     * 查询订单详情数据
     *
     * @param orderInfo
     */
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
//        订单id
        Long id = orderInfo.getId();
//        查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if (orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName", courseName);
        }
        return orderInfo;
    }
}

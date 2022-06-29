package com.atguigu.ggkt.order.service;

import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderFormVo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.atguigu.ggkt.vo.order.OrderInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-28
 */
public interface OrderInfoService extends IService<OrderInfo> {

    //订单列表
    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    //生成订单方法
    Long submitOrder(OrderFormVo orderFormVo);

    //订单id获取订单信息
    OrderInfoVo getOrderInfoById(Long id);

    //更新订单状态 ：已经支付
    void updateOrderStatus(String out_trade_no);
}

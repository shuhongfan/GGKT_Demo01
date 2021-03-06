package com.shf.ggkt.order.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.order.OrderInfo;
import com.shf.ggkt.vo.order.OrderFormVo;
import com.shf.ggkt.vo.order.OrderInfoQueryVo;
import com.shf.ggkt.vo.order.OrderInfoVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    Long submitOrder(OrderFormVo orderFormVo);

    OrderInfoVo getOrderInfoVoById(Long id);

    void updateOrderStatus(String out_trade_no);
}

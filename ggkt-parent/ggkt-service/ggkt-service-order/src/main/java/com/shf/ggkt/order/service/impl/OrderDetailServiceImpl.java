package com.shf.ggkt.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ggkt.model.order.OrderDetail;
import com.shf.ggkt.order.mapper.OrderDetailMapper;
import com.shf.ggkt.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}

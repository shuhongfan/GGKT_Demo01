package com.atguigu.ggkt.order.service;

import java.util.Map;

public interface WXPayService {
    //微信支付
    Map<String, Object> createJsapi(String orderNo);

    //根据订单号调用微信接口查询支付状态
    Map<String, String> queryPayStatus(String orderNo);
}

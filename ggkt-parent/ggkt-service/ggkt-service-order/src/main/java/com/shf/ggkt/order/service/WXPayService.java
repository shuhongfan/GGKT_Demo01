package com.shf.ggkt.order.service;

import java.util.Map;

public interface WXPayService {
    Map<String,Object> createJsapi(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);
}
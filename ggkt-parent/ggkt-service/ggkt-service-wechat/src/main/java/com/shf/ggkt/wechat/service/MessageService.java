package com.shf.ggkt.wechat.service;

import java.util.Map;

public interface MessageService {
    String receiveMessage(Map<String, String> param);

//    订单成功
    void pushPayMessage(Long l);
}

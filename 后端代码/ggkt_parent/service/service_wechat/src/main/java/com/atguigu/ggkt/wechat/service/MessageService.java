package com.atguigu.ggkt.wechat.service;

import java.util.Map;

public interface MessageService {
    //接收微信服务器发送来的消息
    String receiveMessage(Map<String, String> param);

    //订单成功
    void pushPayMessage(long id);
}

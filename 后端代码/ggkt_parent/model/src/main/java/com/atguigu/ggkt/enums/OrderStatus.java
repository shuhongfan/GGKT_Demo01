package com.atguigu.ggkt.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    UNPAID(0,"待支付"),
    PAID(1,"已支付"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    OrderStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}
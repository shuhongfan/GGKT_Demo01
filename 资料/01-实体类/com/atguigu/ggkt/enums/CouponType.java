package com.atguigu.ggkt.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CouponType {
    REGISTER(1,"注册"),
    RECOMMEND(2,"推荐购买");

    @EnumValue
    private Integer code;
    private String comment ;

    CouponType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}
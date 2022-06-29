package com.atguigu.ggkt.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CouponRangeType {
    ALL(1,"通用"),
   ;

    @EnumValue
    private Integer code ;
    private String comment ;

    CouponRangeType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }

}
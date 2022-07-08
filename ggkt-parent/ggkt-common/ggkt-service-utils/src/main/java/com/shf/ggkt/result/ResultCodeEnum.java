package com.shf.ggkt.result;


import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),

    PHONE_CODE_ERROR(211, "手机验证码错误"),

    MTCLOUD_ERROR(210, "直播接口异常"),

    COUPON_GET(220, "优惠券已经领取"),
    COUPON_LIMIT_GET(221, "优惠券已发放完毕"),

    FILE_UPLOAD_ERROR( 21004, "文件上传错误"),
    FILE_DELETE_ERROR( 21005, "文件刪除错误"),

    VOD_PALY_ERROR(209, "请购买后观看"),;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
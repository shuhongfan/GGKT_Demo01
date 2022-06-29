package com.atguigu.ggkt.vo.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("分享签名对象")
@Data
public class WxJsapiSignatureVo {

    private String appId;
    private String nonceStr;
    private long timestamp;
    private String url;
    private String signature;
    //加密用户id
    private String userEedId;
}

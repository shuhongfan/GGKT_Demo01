package com.atguigu.ggkt.order.service.impl;

import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.order.service.WXPayService;
import com.atguigu.ggkt.utils.HttpClientUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {

    //微信支付
    @Override
    public Map<String, Object> createJsapi(String orderNo) {
        //封装微信支付需要参数，使用map集合
        Map<String,String> paramMap = new HashMap<>();
        //正式服务号id  固定值
        paramMap.put("appid", "wxf913bfa3a2c7eeeb");
        //服务号商户号  固定值
        paramMap.put("mch_id", "1481962542");
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", "test");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("total_fee", "1");//为了测试，支付0.01
        paramMap.put("spbill_create_ip", "127.0.0.1");
        paramMap.put("notify_url", "http://glkt.atguigu.cn/api/order/wxPay/notify");
        paramMap.put("trade_type", "JSAPI");//支付类型，按照生成固定金额支付
        /*
        * 设置参数值当前微信用户openid
        * 实现逻辑：第一步 根据订单号获取userid  第二步 根据userid获取openid
        *
        * 因为当前使用测试号，测试号不支持支付功能，为了使用正式服务号进行测试，使用下面写法
        * 获取 正式服务号微信openid
        *
        * 通过其他方式获取正式服务号openid，直接设置
        * */
        //TODO 一会完善代码
        paramMap.put("openid", "oQTXC56lAy3xMOCkKCImHtHoLLN4");

        try {
            //通过httpclient调用微信支付接口
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置请求参数
            String paramXml  = WXPayUtil.generateSignedXml(paramMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9");
            client.setXmlParam(paramXml);
            client.setHttps(true);
            //请求
            client.post();

            //微信支付接口返回数据
            String xml = client.getContent();
            System.out.println("xml:"+xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //进行封装，最终返回
            if(null != resultMap.get("result_code")  && !"SUCCESS".equals(resultMap.get("result_code"))) {
                throw new GgktException(20001,"支付失败");
            }

            //4、再次封装参数
            Map<String, String> parameterMap = new HashMap<>();
            String prepayId = String.valueOf(resultMap.get("prepay_id"));
            String packages = "prepay_id=" + prepayId;
            parameterMap.put("appId", "wxf913bfa3a2c7eeeb");
            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
            parameterMap.put("package", packages);
            parameterMap.put("signType", "MD5");
            parameterMap.put("timeStamp", String.valueOf(new Date().getTime()));
            String sign = WXPayUtil.generateSignature(parameterMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9");
            //返回结果
            Map<String, Object> result = new HashMap();
            result.put("appId", "wxf913bfa3a2c7eeeb");
            result.put("timeStamp", parameterMap.get("timeStamp"));
            result.put("nonceStr", parameterMap.get("nonceStr"));
            result.put("signType", "MD5");
            result.put("paySign", sign);
            result.put("package", packages);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据订单号调用微信接口查询支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //封装微信接口需要参数，使用map
        Map paramMap = new HashMap<>();
        paramMap.put("appid","wxf913bfa3a2c7eeeb");
        paramMap.put("mch_id","1481962542");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

        try {
            //调用接口 httpclient
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap,"MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9"));
            client.setHttps(true);
            client.post();

            //封装返回结果
            String xml = client.getContent();
            System.out.println(xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}

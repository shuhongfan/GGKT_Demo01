package com.shf.ggkt.user.api;

import com.alibaba.fastjson.JSON;
import com.shf.ggkt.model.user.UserInfo;
import com.shf.ggkt.user.service.UserInfoService;
import com.shf.ggkt.utils.JwtHelper;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Controller
@RestController("/api/user/wechat")
public class WechatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    /**
     * 授权跳转
     *
     * @param returnUrl
     * @param request
     * @return
     */
    @GetMapping("authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl, HttpServletRequest request) {
        String url = wxMpService.oauth2buildAuthorizationUrl(
                returnUrl,
                WxConsts.OAUTH2_SCOPE_USER_INFO,
                URLEncoder.encode(returnUrl.replace("guiguketan", "#")));
        return "redirect:" + url;
    }

    @SneakyThrows
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl)  {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = this.wxMpService.oauth2getAccessToken(code);
        String openId = wxMpOAuth2AccessToken.getOpenId();

//        获取微信信息
        System.out.println("【微信网页授权】openId={}"+openId);

        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        System.out.println("【微信网页授权】wxMpUser={}"+ JSON.toJSONString(wxMpUser));

//        获取微信信息添加到数据库
        UserInfo userInfo = userInfoService.getUserInfoByOpenid(openId);
        if(null == userInfo) {
            userInfo = new UserInfo();
            userInfo.setOpenId(openId);
            userInfo.setUnionId(wxMpUser.getUnionId());
            userInfo.setNickName(wxMpUser.getNickname());
            userInfo.setAvatar(wxMpUser.getHeadImgUrl());
            userInfo.setSex(wxMpUser.getSexId());
            userInfo.setProvince(wxMpUser.getProvince());

            userInfoService.save(userInfo);
        }

//        授权完成后，跳转到具体的功能页面
        String token = JwtHelper.createToken(userInfo.getId(), userInfo.getNickName()); // 生成token
        if(returnUrl.indexOf("?") == -1) {
            return "redirect:" + returnUrl + "?token=" + token;
        } else {
            return "redirect:" + returnUrl + "&token=" + token;
        }
    }
}

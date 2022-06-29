package com.atguigu.ggkt.utils;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.initUserLoginVo(request);
        return true;
    }

    private void initUserLoginVo(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println(token);
        //String userId = request.getHeader("userId");
        if (StringUtils.isEmpty(token)) {
            AuthContextHolder.setUserId(1L);
        } else {
            Long userId = JwtHelper.getUserId(token);
            System.out.println("当前用户："+userId);
            if (StringUtils.isEmpty(userId)) {
                AuthContextHolder.setUserId(1L);
            } else {
                AuthContextHolder.setUserId(userId);
            }
        }
    }

}

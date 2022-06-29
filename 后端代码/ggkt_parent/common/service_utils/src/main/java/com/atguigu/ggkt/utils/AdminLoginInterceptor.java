package com.atguigu.ggkt.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public AdminLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.initUserLoginVo(request);
        return true;
    }

    private void initUserLoginVo(HttpServletRequest request){
        String userId = request.getHeader("userId");
        if (StringUtils.isEmpty(userId)) {
            AuthContextHolder.setAdminId(1L);
        } else {
            AuthContextHolder.setAdminId(Long.parseLong(userId));
        }
    }

}

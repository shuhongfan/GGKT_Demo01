package com.atguigu.ggkt.utils;

//import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 获取登录用户信息类
 *
 */
public class AuthContextHolder {

    //后台管理用户id
    private static ThreadLocal<Long> adminId = new ThreadLocal<Long>();
    //会员用户id
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();

    public static Long getAdminId() {
        return adminId.get();
    }

    public static void setAdminId(Long _adminId) {
        adminId.set(_adminId);
    }

    public static Long getUserId(){
        return userId.get();
    }

    public static void setUserId(Long _userId){
        userId.set(_userId);
    }

}

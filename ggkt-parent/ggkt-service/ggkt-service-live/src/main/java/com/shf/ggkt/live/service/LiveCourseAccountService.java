package com.shf.ggkt.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.live.LiveCourseAccount;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-13
 */
public interface LiveCourseAccountService extends IService<LiveCourseAccount> {

    LiveCourseAccount getLiveCourseAccountCourseId(Long id);
}

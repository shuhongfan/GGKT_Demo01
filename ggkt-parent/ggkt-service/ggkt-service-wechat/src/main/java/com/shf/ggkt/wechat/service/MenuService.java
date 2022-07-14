package com.shf.ggkt.wechat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ggkt.model.wechat.Menu;
import com.shf.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findOneMenuInfo();

    List<MenuVo> findMenuInfo();

    void syncMenu();

    void removeMenu();
}

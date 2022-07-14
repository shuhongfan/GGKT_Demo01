package com.shf.ggkt.wechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.shf.ggkt.exception.GgktException;
import com.shf.ggkt.model.wechat.Menu;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.wechat.MenuVo;
import com.shf.ggkt.wechat.service.MenuService;
import com.shf.ggkt.wechat.utils.ConstantPropertiesUtil;
import com.shf.ggkt.wechat.utils.HttpClientUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 公众号菜单删除
     * @return
     */
    @ApiOperation("公众号菜单删除")
    @DeleteMapping("removeMenu")
    public Result removeMenu(){
        menuService.removeMenu();
        return Result.ok();
    }

    /**
     * 同步菜单方法
     * @return
     */
    @ApiOperation("同步菜单方法")
    @GetMapping("syncMenu")
    public Result createMenu(){
        menuService.syncMenu();
        return Result.ok();
    }

    /**
     * 获取access_token
     * @return
     */
    @ApiOperation("获取access_token")
    @GetMapping("getAccessToken")
    public Result getAccessToken() {
        //拼接请求地址
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://api.weixin.qq.com/cgi-bin/token");
        buffer.append("?grant_type=client_credential");
        buffer.append("&appid=%s");
        buffer.append("&secret=%s");

//        设置路径参数
        String url = String.format(
                buffer.toString(),
                ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);

        try {
            //发送http请求
            String tokenString = HttpClientUtils.get(url);
//            获取access_token
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            String access_token = jsonObject.getString("access_token");
            return Result.ok(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(20001, "获取access_token失败");
        }
    }


    /**
     * 获取所有菜单，按照一级和二级菜单封装
     * @return
     */
    @ApiOperation("获取所有菜单，按照一级和二级菜单封装")
    @GetMapping("findMenuInfo")
    public Result findMenuInfo(){
        List<MenuVo> menuList = menuService.findMenuInfo();
        return Result.ok(menuList);
    }

    /**
     * 获取所有的一级菜单
     * @return
     */
    @ApiOperation("获取所有的一级菜单")
    @GetMapping("findOneMenuInfo")
    public Result findOneMenuInfo(){
        List<Menu> list = menuService.findOneMenuInfo();
        return Result.ok(list);
    }

    /**
     * 获取
     * @param id
     * @return
     */
    @ApiOperation("获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    /**
     * 新增
     * @param menu
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.ok();
    }

    /**
     * 修改
     * @param menu
     * @return
     */
    @ApiOperation("修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id列表删除
     * @param idList
     * @return
     */
    @ApiOperation("根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.ok();
    }
}


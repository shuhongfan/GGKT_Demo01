package com.shf.ggkt.activity.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ggkt.activity.service.CouponInfoService;
import com.shf.ggkt.model.activity.CouponInfo;
import com.shf.ggkt.model.activity.CouponUse;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.activity.CouponUseQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {
    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * 获取分页列表
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当期页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        Page<CouponInfo> pageModel = couponInfoService.page(pageParam);
        return Result.ok(pageModel);
    }

    /**
     * 获取优惠券
     *
     * @param id
     * @return
     */
    @ApiOperation("获取优惠券")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        return Result.ok(couponInfo);
    }

    /**
     * 新增优惠券
     *
     * @param couponInfo
     * @return
     */
    @ApiOperation("新增优惠券")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok();
    }

    /**
     * 修改优惠券
     *
     * @param couponInfo
     * @return
     */
    @ApiOperation("修改优惠券")
    @PutMapping("update")
    public Result updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok();
    }

    /**
     * 删除优惠券
     *
     * @param id
     * @return
     */
    @ApiOperation("删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id列表删除优惠券
     *
     * @param idList
     * @return
     */
    @ApiOperation("根据id列表删除优惠券")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        couponInfoService.removeByIds(idList);
        return Result.ok();
    }


    /**
     * 获取已经使用优惠券列表（条件查询分页）
     * @param page
     * @param limit
     * @param couponUseQueryVo
     * @return
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("couponUse/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable Long limit,
            @ApiParam(name = "couponUseVo",value = "查询对象",required = false) CouponUseQueryVo couponUseQueryVo) {
        Page<CouponUse> pageParam = new Page<>(page, limit);
        Page<CouponUse> pageModel = couponInfoService.selectCouponUsePage(pageParam, couponUseQueryVo);
        return Result.ok(pageModel);
    }
}


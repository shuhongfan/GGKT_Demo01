package com.shf.ggkt.order.api;

import com.shf.ggkt.order.service.OrderInfoService;
import com.shf.ggkt.result.Result;
import com.shf.ggkt.vo.order.OrderFormVo;
import com.shf.ggkt.vo.order.OrderInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order/orderInfo")
public class OrderInfoApiController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 生成订单方法
     * @param orderFormVo
     * @return
     */
    @ApiOperation("新增点播课程订单")
    @PostMapping("submitOrder")
    public Result submitOrder(@RequestBody OrderFormVo orderFormVo) {
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return Result.ok(orderId);
    }

    /**
     * 订单id获取订单信息
     * @param id
     * @return
     */
    @ApiOperation(value = "订单id获取订单信息")
    @GetMapping("getInfo/{id}")
    public Result getInfo(@PathVariable Long id) {
        OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoVoById(id);
        return Result.ok(orderInfoVo);
    }

}

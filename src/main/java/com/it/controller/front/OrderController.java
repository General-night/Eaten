package com.it.controller.front;

import com.it.common.Result;
import com.it.entity.front.Orders;
import com.it.service.front.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NightGeneral
 * @date 2022/4/8 1:10
 */

@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 用户下单
     *
     * @param orders 订单信息
     * @return 是否下单成功
     */
    @PostMapping("submit")
    public Result<String> submit(@RequestBody Orders orders) {

        log.info("客户端-下单，入参：{}", orders);

        Result<String> res = orderService.submit(orders);

        return null;
    }
}

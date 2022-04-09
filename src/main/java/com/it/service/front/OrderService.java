package com.it.service.front;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.front.Orders;

/**
 * @author NightGeneral
 * @date 2022/4/8 0:52
 */
public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     *
     * @param orders 订单信息
     * @return 是否下单成功
     */
    Result<String> submit(Orders orders);
}

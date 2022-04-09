package com.it.service.impl.front;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.front.OrderDetail;
import com.it.mapper.front.OrderDetailMpper;
import com.it.service.front.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author NightGeneral
 * @date 2022/4/8 0:54
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMpper, OrderDetail> implements OrderDetailService {
}

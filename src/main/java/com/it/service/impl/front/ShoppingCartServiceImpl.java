package com.it.service.impl.front;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.front.ShoppingCart;
import com.it.mapper.front.ShoppingCartMapper;
import com.it.service.front.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author NightGeneral
 * @date 2022/4/7 16:37
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}

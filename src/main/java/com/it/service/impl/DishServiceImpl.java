package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Dish;
import com.it.mapper.DishMapper;
import com.it.service.DishService;
import org.springframework.stereotype.Service;

/**
 * 菜品业务层实现类
 *
 * @author NightGeneral
 * @date 2022/4/1 14:30
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

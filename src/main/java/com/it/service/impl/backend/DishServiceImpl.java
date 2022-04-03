package com.it.service.impl.backend;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.backend.Dish;
import com.it.mapper.backend.DishMapper;
import com.it.service.backend.DishService;
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

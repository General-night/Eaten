package com.it.service.impl.backend;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dto.SetmealDto;
import com.it.entity.backend.SetmealDish;
import com.it.mapper.backend.SetmealDishMapper;
import com.it.service.backend.SetmealDishService;
import org.springframework.stereotype.Service;

/**
 * @author NightGeneral
 * @date 2022/4/4 17:27
 */

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}

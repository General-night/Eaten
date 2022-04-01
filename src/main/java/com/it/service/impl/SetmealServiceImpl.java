package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Setmeal;
import com.it.mapper.SetmealMapper;
import com.it.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * 套餐业务层实现类
 *
 * @author NightGeneral
 * @date 2022/4/1 14:32
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}

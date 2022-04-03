package com.it.service.impl.backend;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.backend.Setmeal;
import com.it.mapper.backend.SetmealMapper;
import com.it.service.backend.SetmealService;
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

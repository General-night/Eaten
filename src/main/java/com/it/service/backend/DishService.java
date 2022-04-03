package com.it.service.backend;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;

/**
 * @author NightGeneral
 * @date 2022/4/1 14:28
 */
public interface DishService extends IService<Dish> {

    /**
     * 添加菜品
     *
     * @param dishDto 菜品信息
     * @return 是否添加成功
     */
    Result<String> addWithFlavors(DishDto dishDto);
}

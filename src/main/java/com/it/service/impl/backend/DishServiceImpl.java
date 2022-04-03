package com.it.service.impl.backend;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;
import com.it.entity.backend.DishFlavor;
import com.it.mapper.backend.DishMapper;
import com.it.service.backend.DishFlavorService;
import com.it.service.backend.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜品业务层实现类
 *
 * @author NightGeneral
 * @date 2022/4/1 14:30
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService service;

    /**
     * 添加菜品
     *
     * @param dishDto 菜品信息
     * @return 是否添加成功
     */
    @Override
    public Result<String> addWithFlavors(DishDto dishDto) {

        // 添加菜品
        save(dishDto);

        // 添加菜品口味
        // 获取口味集合
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 获取菜品ID
        Long id = dishDto.getId();

        // 添加口味中菜品ID
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
        }

        service.saveBatch(flavors);

        return Result.success("添加成功");
    }
}

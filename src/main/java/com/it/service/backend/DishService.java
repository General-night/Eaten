package com.it.service.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * 菜品管理初始化
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     菜品名称
     * @return 菜品集合
     */
    Result<IPage<DishDto>> page(int page, int pageSize, String name);
}

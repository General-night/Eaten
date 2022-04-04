package com.it.service.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;

import java.util.List;

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

    /**
     * 根据指定ID获取菜品信息
     *
     * @param id 指定ID值
     * @return 菜品信息
     */
    Result<DishDto> getById(Long id);

    /**
     * 修改菜品信息
     *
     * @param dishDto 修改信息
     * @return 是否修改成功
     */
    Result<String> update(DishDto dishDto);

    /**
     * 修改指定ID菜品的状态
     *
     * @param flag   是否停售
     * @param idsStr 指定ID
     * @return 停售是否成功
     */
    Result<String> updateStatus(String flag, String[] idsStr);

    /**
     * 根据指定ID进行删除或批量删除
     *
     * @param ids 指定ID
     * @return 是否删除成功
     */
    Result<String> deleteById(String[] ids);


    /**
     * 根据指定ID查询对应的菜品
     *
     * @param categoryId 指定的菜品分类ID
     * @return 菜品集合
     */
    Result<List<Dish>> list(Long categoryId, String name);
}

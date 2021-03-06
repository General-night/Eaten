package com.it.service.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.entity.backend.Setmeal;

import java.util.List;

/**
 * 套餐业务层接口
 *
 * @author NightGeneral
 * @date 2022/4/1 14:29
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 套餐管理，初始化页面
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @param name     套餐名称关键字
     * @return 套餐信息集合
     */
    Result<IPage<SetmealDto>> page(Integer page, Integer pageSize, String name);


    /**
     * 添加套餐
     *
     * @param setmealDto 套餐信息
     * @return 是否添加成功
     */
    Result<String> addSetmeal(SetmealDto setmealDto);


    /**
     * 根据指定ID获取套餐信息
     *
     * @param id 指定套餐ID
     * @return 套餐信息
     */
    Result<SetmealDto> getById(Long id);


    /**
     * 根据指定ID修改套餐信息
     *
     * @param setmealDto 修改信息
     * @return 是否修改成功
     */
    Result<String> updateById(SetmealDto setmealDto);


    /**
     * 根据指定ID删除套餐信息
     *
     * @param idList 指定的套餐ID
     * @return 是否删除成功
     */
    Result<String> deleteByIds(List<Long> idList);
}

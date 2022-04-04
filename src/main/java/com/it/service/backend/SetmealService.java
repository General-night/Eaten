package com.it.service.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.entity.backend.Setmeal;

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
}

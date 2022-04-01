package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.Category;

/**
 * 分类业务层接口
 *
 * @author NightGeneral
 * @date 2022/3/31 16:59
 */

public interface CategoryService extends IService<Category> {

    /**
     * 分类管理页面初始化
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @return 查询数据
     */
    Result<IPage<Category>> page(Integer page, Integer pageSize);
}

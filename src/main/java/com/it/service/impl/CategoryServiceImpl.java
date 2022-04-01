package com.it.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.Category;
import com.it.mapper.CategoryMapper;
import com.it.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类业务层实现类
 *
 * @author NightGeneral
 * @date 2022/3/31 17:02
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 分类管理页面初始化
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @return 查询数据
     */
    @Override
    public Result<IPage<Category>> page(Integer page, Integer pageSize) {

        // 创建分页器
        IPage<Category> pageInfo = new Page<>(page, pageSize);

        // 查询数据
        page(pageInfo);

        return Result.success(pageInfo);
    }
}

package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.Category;
import com.it.entity.Dish;
import com.it.entity.Setmeal;
import com.it.exception.ConsumerException;
import com.it.mapper.CategoryMapper;
import com.it.service.CategoryService;
import com.it.service.DishService;
import com.it.service.SetmealService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分类业务层实现类
 *
 * @author NightGeneral
 * @date 2022/3/31 17:02
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

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

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return 是否成功
     */
    @Override
    public Result<String> addCategory(Category category) {

        // 判断分类名称是否存在
        String name = category.getName();
        Category cg = getOne(new LambdaQueryWrapper<Category>().eq(Category::getName, name));

        // 存在，抛出异常
        if (!ObjectUtils.equals(cg, null)) {
            throw new ConsumerException(name + " 分类已存在");
        }

        // 不存在，执行添加操作
        save(category);

        return Result.success(null);
    }

    /**
     * 根据ID删除分类信息
     *
     * @param id 指定的ID值
     * @return 是否删除成功
     */
    @Override
    public Result<String> removeById(Long id) {

        // 判断该类是否关联了菜品或套餐
        int count1 = dishService.count(
                new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, id)
        );

        int count2 = setmealService.count(
                new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId, id)
        );

        // 如果关联则抛出业务层异常
        if (count1 > 0 || count2 > 0) {
            throw new ConsumerException("该类已有关联数据，禁止删除");
        }

        // 如果没有关联数据，则执行分类删除
        super.removeById(id);

        return Result.success("删除成功");
    }
}

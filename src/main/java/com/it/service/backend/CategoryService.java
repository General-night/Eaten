package com.it.service.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.backend.Category;

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

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return 是否成功
     */
    Result<String> addCategory(Category category);

    /**
     * 根据ID删除分类信息
     *
     * @param id 指定的ID值
     * @return 是否删除成功
     */
    Result<String> removeById(Long id);

    /**
     * 根据ID修改分类信息
     *
     * @param category 要修改的信息
     * @return 是否修改成功
     */
    Result<String> update(Category category);
}

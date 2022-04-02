package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.entity.Category;
import com.it.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类控制层
 *
 * @author NightGeneral
 * @date 2022/3/31 16:47
 */

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    /**
     * 分类管理页面初始化
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @return 查询数据
     */
    @GetMapping("page")
    public Result<IPage<Category>> page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        log.info("分类管理页面初始化，入参：page={}，pageSize={}", page, pageSize);

        Result<IPage<Category>> res = service.page(page, pageSize);

        log.info("分类管理页面初始化，出参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return 是否成功
     */
    @PostMapping
    public Result<String> addCategory(@RequestBody Category category) {

        log.info("添加分类，入参：{}", JSON.toJSONString(category));

        Result<String> res = service.addCategory(category);

        log.info("添加分类，出参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 根据ID删除分类信息
     *
     * @param id 指定的ID值
     * @return 是否删除成功
     */
    @DeleteMapping
    public Result<String> removeById(Long id) {

        log.info("删除分类，入参：ID = {}", id);

        Result<String> res = service.removeById(id);

        log.info("删除分类，入参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 根据ID修改分类信息
     *
     * @param category 要修改的信息
     * @return 是否修改成功
     */
    @PutMapping
    public Result<String> updateById(@RequestBody Category category) {

        log.info("修改分类信息，入参：{}", category);

        Result<String> res = service.update(category);

        log.info("修改分类信息，出参：{}", JSON.toJSONString(res));

        return res;
        
    }
}

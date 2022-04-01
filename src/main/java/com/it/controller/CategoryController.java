package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.entity.Category;
import com.it.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

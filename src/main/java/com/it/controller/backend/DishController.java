package com.it.controller.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.service.backend.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品分类控制层
 *
 * @author NightGeneral
 * @date 2022/4/3 1:25
 */

@Slf4j
@RestController
@RequestMapping("dish")
public class DishController {

    @Autowired
    private DishService service;

    /**
     * 添加菜品
     *
     * @param dishDto 菜品信息
     * @return 是否添加成功
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDto dishDto) {

        log.info("添加菜品，入参：{}", dishDto);

        Result<String> res = service.addWithFlavors(dishDto);

        log.info("添加菜品，出参：{}", res);

        return res;
    }

    /**
     * 菜品管理初始化
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     菜品名称
     * @return 菜品集合
     */
    @GetMapping("page")
    public Result<IPage<DishDto>> page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    String name) {

        log.info("菜品管理初始化，入参：page={}，pageSize={}，name={}", page, pageSize, name);

        Result<IPage<DishDto>> res = service.page(page, pageSize, name);

        log.info("菜品管理初始化，出参：{}", JSON.toJSONString(res));

        return res;
    }
}

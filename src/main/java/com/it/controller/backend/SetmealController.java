package com.it.controller.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.service.backend.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐管理
 *
 * @author NightGeneral
 * @date 2022/4/4 10:47
 */

@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService service;

    /**
     * 套餐管理，初始化页面
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @param name     套餐名称关键字
     * @return 套餐信息集合
     */
    @GetMapping("page")
    public Result<IPage<SetmealDto>> page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          String name) {

        log.info("套餐管理-初始化页面，入参：page={}，pageSize={}，name={}", page, pageSize, name);

        Result<IPage<SetmealDto>> res = service.page(page, pageSize, name);

        log.info("套餐管理-初始化页面，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 添加套餐
     *
     * @param setmealDto 套餐信息
     * @return 是否添加成功
     */
    @PostMapping
    public Result<String> addSetmeal(@RequestBody SetmealDto setmealDto) {

        log.info("套餐管理-添加套餐，入参：{}", JSON.toJSONString(setmealDto));

        Result<String> res = service.addSetmeal(setmealDto);

        log.info("套餐管理-添加套餐，出参：{}", JSON.toJSONString(res));

        return res;
    }
}

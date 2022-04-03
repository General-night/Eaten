package com.it.controller.backend;

import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.service.backend.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

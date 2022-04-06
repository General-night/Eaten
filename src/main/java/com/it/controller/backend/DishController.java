package com.it.controller.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;
import com.it.service.backend.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private DishService dishService;

    /**
     * 添加菜品
     *
     * @param dishDto 菜品信息
     * @return 是否添加成功
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDto dishDto) {

        log.info("添加菜品，入参：{}", dishDto);

        Result<String> res = dishService.addWithFlavors(dishDto);

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

        Result<IPage<DishDto>> res = dishService.page(page, pageSize, name);

        log.info("菜品管理初始化，出参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 根据指定ID获取菜品信息
     *
     * @param id 指定ID值
     * @return 菜品信息
     */
    @GetMapping("{id}")
    public Result<DishDto> getById(@PathVariable Long id) {

        log.info("菜品管理-根据ID获取菜品信息，入参：id = {}", id);

        Result<DishDto> res = dishService.getById(id);

        log.info("菜品管理-根据ID获取菜品信息，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 修改菜品信息
     *
     * @param dishDto 修改信息
     * @return 是否修改成功
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {

        log.info("菜品管理-修改功能，入参：{}", JSON.toJSONString(dishDto));

        Result<String> res = dishService.update(dishDto);

        log.info("菜品管理-修改功能，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 修改指定ID菜品的状态
     *
     * @param flag   是否停售
     * @param idsStr 指定ID
     * @return 停售是否成功
     */
    @PostMapping("status/{flag}")
    public Result<String> updateStatus(@PathVariable String flag, @RequestParam("ids") String idsStr) {

        log.info("菜品管理-修改状态，入参：flag={}，ids={}", flag, idsStr);

        // 获取ID数组
        String[] ids = idsStr.split(",");

        Result<String> res = dishService.updateStatus(flag, ids);

        log.info("菜品管理-修改状态，出参：{}", res);

        return res;
    }


    /**
     * 根据指定ID进行删除或批量删除
     *
     * @param idsStr 指定ID
     * @return 是否删除成功
     */
    @DeleteMapping
    public Result<String> deleteById(@RequestParam("ids") String idsStr) {

        log.info("菜单管理-删除，入参：{}", idsStr);

        String[] ids = idsStr.split(",");

        Result<String> res = dishService.deleteById(ids);

        log.info("菜单管理-删除，出参：{}", res);

        return res;
    }


    /**
     * 根据指定ID查询对应的菜品
     *
     * @param categoryId 指定的菜品分类ID
     * @return 菜品集合
     */
    @GetMapping("list")
    // 这里可以用 Dish来接参数
    public Result<List<Dish>> list(Long categoryId, String name) {

        log.info("套餐管理-获取菜品列表，入参：{}", categoryId);

        Result<List<Dish>> res = dishService.list(categoryId, name);

        log.info("套餐管理-获取菜品列表，出参：{}", JSON.toJSONString(res));

        return res;
    }
}

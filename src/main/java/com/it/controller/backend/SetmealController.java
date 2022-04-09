package com.it.controller.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.entity.backend.Setmeal;
import com.it.service.backend.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 根据指定ID获取套餐信息
     *
     * @param id 指定套餐ID
     * @return 套餐信息
     */
    @GetMapping("{id}")
    public Result<SetmealDto> getById(@PathVariable Long id) {

        log.info("套餐管理-数据回显，入参：id={}", id);

        Result<SetmealDto> res = service.getById(id);

        log.info("套餐管理-数据回显，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 根据指定ID修改套餐信息
     *
     * @param setmealDto 修改信息
     * @return 是否修改成功
     */
    @PutMapping
    public Result<String> updateById(@RequestBody SetmealDto setmealDto) {

        log.info("套餐管理-修改套餐信息，入参：{}", setmealDto);

        Result<String> res = service.updateById(setmealDto);

        log.info("套餐管理-修改套餐信息，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 根据指定ID删除套餐信息
     *
     * @param idsStr 指定的套餐ID
     * @return 是否删除成功
     */
    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam("ids") String idsStr) {

        log.info("套餐管理-删除，入参：ids={}", idsStr);

        List<Long> idList = new ArrayList<>();

        for (String id : idsStr.split(",")) {
            idList.add(Long.valueOf(id));
        }

        Result<String> res = service.deleteByIds(idList);

        log.info("套餐管理-删除，出参：{}", res);

        return res;
    }


    /**
     * 根据指定ID设置套餐销售状态
     *
     * @param flag   是否销售
     * @param idsStr 指定的ID
     * @return 是否设置成功
     */
    @PostMapping("status/{flag}")
    public Result<String> updateStatus(@PathVariable Integer flag, @RequestParam("ids") String idsStr) {

        log.info("套餐管理-状态，入参：flag={}，ids={}", flag, idsStr);

        // 将前端传递的ID字符串转为集合
        List<Long> idList = new ArrayList<>();

        for (String id : idsStr.split(",")) {
            idList.add(Long.valueOf(id));
        }

        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(flag);

        // 批量更新套餐销售状态
        service.update(setmeal,
                new LambdaQueryWrapper<Setmeal>()
                        .in(Setmeal::getId, idList)
        );

        Result<String> res = Result.success("切换成功");

        log.info("套餐管理-状态，入参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 根据指定ID获取套餐下的菜品信息
     *
     * @param setmeal 指定的套餐信息
     * @return 套餐下菜品信息集合
     */
    @GetMapping("list")
    public Result<List<Setmeal>> list(Setmeal setmeal) {

        List<Setmeal> setmealList = service.list(
                new LambdaQueryWrapper<Setmeal>()
                        .eq(Setmeal::getCategoryId, setmeal.getCategoryId())
                        .eq(Setmeal::getStatus, setmeal.getStatus())
                        .orderByDesc(Setmeal::getUpdateTime)
        );

        return Result.success(setmealList);
    }
}

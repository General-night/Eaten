package com.it.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.common.Result;
import com.it.entity.front.ShoppingCart;
import com.it.service.front.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author NightGeneral
 * @date 2022/4/7 16:35
 */


@Slf4j
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ShoppingCartService service;

    /**
     * 添加购物车
     *
     * @param shoppingCart 购物车信息
     * @return 是否添加成功
     */
    @PostMapping("add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {

        log.info("客户端-添加购物车，入参：{}", shoppingCart);

        // 设置用户id，指定当前是哪个用户的购物车数据
        Long userId = (Long) session.getAttribute("user");
        shoppingCart.setUserId(userId);

        // 查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        if (!ObjectUtils.equals(dishId, null)) {
            // 添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 查询当前菜品或套餐是否在购物车中
        ShoppingCart one = service.getOne(queryWrapper);

        if (!ObjectUtils.equals(one, null)) {
            // 如果已经存在，就在原来的基础上加一
            one.setNumber(one.getNumber() + 1);
            one.setCreateTime(LocalDateTime.now());
            service.updateById(one);
        } else {
            // 如果不存在，则添加到购物车，数量默认为是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            service.save(shoppingCart);
            one = shoppingCart;
        }

        return Result.success(one);
    }


    @PostMapping("sub")
    public Result<ShoppingCart> add(@RequestBody Map<String, String> params) {

        log.info("客户端-删除购物车，入参：{}", params);

        String dishId = params.get("dishId");
        String setmealId = params.get("setmealId");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.equals(dishId, null)) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }

        ShoppingCart one = service.getOne(queryWrapper);
        one.setNumber(one.getNumber() - 1);

        service.updateById(one);

        return Result.success(one);
    }


    /**
     * 查看购物车
     *
     * @return 当前用户已添加的购物车信息列表
     */
    @GetMapping("list")
    public Result<List<ShoppingCart>> list() {

        log.info("客户端-查看购物车...");

        List<ShoppingCart> cartList = service.list(
                new LambdaQueryWrapper<ShoppingCart>()
                        .eq(ShoppingCart::getUserId, session.getAttribute("user"))
                        .orderByAsc(ShoppingCart::getCreateTime)
        );

        return Result.success(cartList);
    }


    /**
     * 清空购物车
     *
     * @return 执行是否成功
     */
    @DeleteMapping("clean")
    public Result<String> clean() {

        log.info("客户端-清空购物车");

        service.remove(
                new LambdaQueryWrapper<ShoppingCart>()
                        .eq(ShoppingCart::getUserId, session.getAttribute("user"))
        );

        return Result.success(null);
    }
}

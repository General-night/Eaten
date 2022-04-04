package com.it.service.impl.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;
import com.it.entity.backend.DishFlavor;
import com.it.exception.ConsumerException;
import com.it.mapper.backend.DishMapper;
import com.it.service.backend.CategoryService;
import com.it.service.backend.DishFlavorService;
import com.it.service.backend.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 菜品业务层实现类
 *
 * @author NightGeneral
 * @date 2022/4/1 14:30
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     *
     * @param dishDto 菜品信息
     * @return 是否添加成功
     */
    @Override
    @Transactional
    public Result<String> addWithFlavors(DishDto dishDto) {

        // 添加菜品
        try {
            save(dishDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConsumerException("菜品名称已存在");
        }

        // 添加菜品口味
        // 获取口味集合
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 获取菜品ID
        Long id = dishDto.getId();

        // 添加口味中菜品ID
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
        }

        dishFlavorService.saveBatch(flavors);

        return Result.success("添加成功");
    }

    /**
     * 菜品管理初始化
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     菜品名称
     * @return 菜品集合
     */
    @Override
    public Result<IPage<DishDto>> page(int page, int pageSize, String name) {

        IPage<Dish> pageInfo = new Page<>(page, pageSize);
        // 注意：不能用下面的进行分页，因为没有dishDto这个表，即使通过注解指定为dish表
        //      但是在分页时会查询字段，而dish表中没有dishDto自定义的字段
        IPage<DishDto> dishDtoPage = new Page<>();

        page(pageInfo,
                new LambdaQueryWrapper<Dish>()
                        .like(StringUtils.isNotBlank(name), Dish::getName, name)
                        .orderByDesc(Dish::getUpdateTime)
        );

        // 将pageInfo内容拷贝到dishDtoPage中，并忽略records属性
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        // 获取分页后菜品的集合
        List<Dish> dishList = pageInfo.getRecords();
        List<DishDto> dishDtoList = new ArrayList<>();

        // 遍历dishList集合，通过category_id获取categoryName属性值
        for (Dish dish : dishList) {
            Long categoryId = dish.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setCategoryName(categoryName);

            dishDtoList.add(dishDto);
        }

        dishDtoPage.setRecords(dishDtoList);

        return Result.success(dishDtoPage);
    }


    /**
     * 根据指定ID获取菜品信息，用于回显
     *
     * @param id 指定ID值
     * @return 菜品信息
     */
    @Override
    public Result<DishDto> getById(Long id) {
        // 获取菜品信息
        Dish dish = super.getById(id);

        // 获取菜品口味集合
        List<DishFlavor> flavorList = dishFlavorService.list(
                new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id)
        );

        // 用dishDto传输数据
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        dishDto.setFlavors(flavorList);

        return Result.success(dishDto);
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto 修改信息
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public Result<String> update(DishDto dishDto) {

        try {
            // 更新dish基本信息
            updateById(dishDto);

            // 获取更新后的菜品口味集合
            List<DishFlavor> flavors = dishDto.getFlavors();

            // 根据菜品ID删除原有的菜品口味
            dishFlavorService.remove(
                    new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, dishDto.getId())
            );

            // 更新菜品口味表
            dishFlavorService.saveBatch(flavors);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConsumerException("菜品名称已存在");
        }

        return Result.success("修改成功");
    }

    /**
     * 修改指定ID菜品的状态
     *
     * @param flag   是否停售
     * @param idsStr 指定ID
     * @return 停售是否成功
     */
    @Override
    public Result<String> updateStatus(String flag, String[] idsStr) {

        Dish dish = new Dish();
        for (String id : idsStr) {
            dish.setId(Long.valueOf(id));
            dish.setStatus(Integer.valueOf(flag));
            updateById(dish);
        }

        return Result.success(Objects.equals(flag, "1") ? "已起售" : "已停售");
    }

    /**
     * 根据指定ID进行删除或批量删除
     *
     * @param ids 指定ID
     * @return 是否删除成功
     */
    @Override
    public Result<String> deleteById(String[] ids) {

        for (String id : ids) {
            removeById(Long.valueOf(id));
        }

        return Result.success("删除成功");
    }

    /**
     * 根据指定ID查询对应的菜品
     *
     * @param categoryId 指定的菜品分类ID
     * @return 菜品集合
     */
    @Override
    public Result<List<Dish>> list(Long categoryId, String name) {

        List<Dish> dishList = list(
                new LambdaQueryWrapper<Dish>()
                        .eq(Dish::getCategoryId, categoryId)
                        .or()
                        .like(StringUtils.isNotBlank(name), Dish::getName, name)
        );

        return Result.success(dishList);
    }
}

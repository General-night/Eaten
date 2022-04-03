package com.it.service.impl.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.dto.DishDto;
import com.it.entity.backend.Dish;
import com.it.entity.backend.DishFlavor;
import com.it.mapper.backend.DishMapper;
import com.it.service.backend.CategoryService;
import com.it.service.backend.DishFlavorService;
import com.it.service.backend.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Result<String> addWithFlavors(DishDto dishDto) {

        // 添加菜品
        save(dishDto);

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
     * 根据指定ID获取菜品信息
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

        // 获取菜品所属的分类名称
        String categoryName = categoryService.getById(dish.getCategoryId()).getName();

        // 用dishDto传输数据
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        dishDto.setFlavors(flavorList);
        dishDto.setCategoryName(categoryName);

        return Result.success(dishDto);
    }
}

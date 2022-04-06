package com.it.service.impl.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.entity.backend.Category;
import com.it.entity.backend.Setmeal;
import com.it.entity.backend.SetmealDish;
import com.it.exception.ConsumerException;
import com.it.mapper.backend.SetmealMapper;
import com.it.service.backend.CategoryService;
import com.it.service.backend.SetmealDishService;
import com.it.service.backend.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐业务层实现类
 *
 * @author NightGeneral
 * @date 2022/4/1 14:32
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 套餐管理，初始化页面
     *
     * @param page     当前页码
     * @param pageSize 每页显示条数
     * @param name     套餐名称关键字
     * @return 套餐信息集合
     */
    @Override
    public Result<IPage<SetmealDto>> page(Integer page, Integer pageSize, String name) {

        IPage<Setmeal> pageInfo = new Page<>(page, pageSize);
        IPage<SetmealDto> dtoPageInfo = new Page<>();

        page(pageInfo,
                new LambdaQueryWrapper<Setmeal>()
                        .like(StringUtils.isNotBlank(name), Setmeal::getName, name)
                        .orderByDesc(Setmeal::getUpdateTime)
        );

        BeanUtils.copyProperties(pageInfo, dtoPageInfo, "records");

        List<Setmeal> setmealList = pageInfo.getRecords();
        List<SetmealDto> setmealDtoList = new ArrayList<>();

        for (Setmeal setmeal : setmealList) {
            String categoryName = categoryService.getOne(
                    new LambdaQueryWrapper<Category>()
                            .eq(Category::getId, setmeal.getCategoryId())
            ).getName();
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(categoryName);
            setmealDtoList.add(setmealDto);
        }

        dtoPageInfo.setRecords(setmealDtoList);

        return Result.success(dtoPageInfo);
    }

    /**
     * 添加套餐
     *
     * @param setmealDto 套餐信息
     * @return 是否添加成功
     */
    @Override
    @Transactional
    public Result<String> addSetmeal(SetmealDto setmealDto) {

        try {
            // 添加套餐信息
            save(setmealDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConsumerException("套餐名称已存在");
        }

        // 获取套餐中的菜品信息集合
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        // 获取套餐ID
        Long id = setmealDto.getId();

        // 遍历菜品集合，逐一添加套餐ID
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }

        // 添加套餐与菜品中间表信息
        setmealDishService.saveBatch(setmealDishes);

        return Result.success("添加成功");
    }

    /**
     * 根据指定ID获取套餐信息
     *
     * @param id 指定套餐ID
     * @return 套餐信息
     */
    @Override
    public Result<SetmealDto> getById(Long id) {

        Setmeal setmeal = super.getById(id);
        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal, setmealDto);

        List<SetmealDish> dishList = setmealDishService.list(
                new LambdaQueryWrapper<SetmealDish>()
                        .eq(SetmealDish::getSetmealId, id)
        );

        setmealDto.setSetmealDishes(dishList);

        return Result.success(setmealDto);
    }

    /**
     * 根据指定ID修改套餐信息
     *
     * @param setmealDto 修改信息
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public Result<String> updateById(SetmealDto setmealDto) {

        // 修改套餐基本信息
        try {
            super.updateById(setmealDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConsumerException("套餐名称已存在");
        }

        // 获取套餐ID
        Long id = setmealDto.getId();

        // 删除套餐菜品关联表中数据
        setmealDishService.removeById(id);

        // 设置套餐菜品的套餐ID
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }

        // 插入新的套餐菜品数据
        setmealDishService.saveBatch(setmealDishes);

        return Result.success("修改成功");
    }

    /**
     * 根据指定ID删除套餐信息
     *
     * @param idList 指定的套餐ID
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public Result<String> deleteByIds(List<Long> idList) {

        // 查询套餐状态，确定是否可用删除

        // 删除套餐表数据
        removeByIds(idList);

        // 删除套餐菜品表数据
        setmealDishService.remove(
                new LambdaQueryWrapper<SetmealDish>()
                        .in(SetmealDish::getSetmealId, idList)
        );

        return Result.success("删除成功");
    }
}

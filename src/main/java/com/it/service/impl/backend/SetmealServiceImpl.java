package com.it.service.impl.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.dto.SetmealDto;
import com.it.entity.backend.Category;
import com.it.entity.backend.Setmeal;
import com.it.mapper.backend.SetmealMapper;
import com.it.service.backend.CategoryService;
import com.it.service.backend.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

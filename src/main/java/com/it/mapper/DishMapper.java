package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/1 14:27
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

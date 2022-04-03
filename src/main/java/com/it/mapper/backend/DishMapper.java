package com.it.mapper.backend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.backend.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/1 14:27
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

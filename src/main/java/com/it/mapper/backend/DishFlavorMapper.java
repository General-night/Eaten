package com.it.mapper.backend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.backend.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品口味
 *
 * @author NightGeneral
 * @date 2022/4/3 16:20
 */

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}

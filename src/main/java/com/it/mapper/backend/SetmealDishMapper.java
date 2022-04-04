package com.it.mapper.backend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dto.SetmealDto;
import com.it.entity.backend.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/4 17:25
 */

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}

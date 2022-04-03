package com.it.mapper.backend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.backend.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/3/31 17:22
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}

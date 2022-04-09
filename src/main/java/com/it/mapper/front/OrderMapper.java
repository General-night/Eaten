package com.it.mapper.front;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.front.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/8 0:51
 */

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}

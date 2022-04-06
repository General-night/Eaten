package com.it.mapper.front;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.front.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/6 16:17
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

package com.it.mapper.backend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.backend.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:57
 * @description
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

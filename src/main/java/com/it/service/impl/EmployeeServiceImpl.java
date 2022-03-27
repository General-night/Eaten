package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Employee;
import com.it.mapper.EmployeeMapper;
import com.it.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:58
 * @description
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.Employee;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:58
 * @description 员工业务层
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 员工登录业务
     *
     * @param employee 员工用户名和密码信息
     * @return 响应对象
     */
    Result<Employee> login(Employee employee);
}

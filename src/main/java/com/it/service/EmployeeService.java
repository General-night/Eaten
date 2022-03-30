package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * 添加员工信息
     *
     * @param employee 员工信息
     * @return 是否添加成功
     */
    Result<String> addEmployee(Employee employee);

    /**
     * 初始化或根据员工昵称模糊查询，并对结果集进行分页处理
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     员工昵称
     * @return 分页器
     */
    IPage<Employee> page(Integer page, Integer pageSize, String name);


    /**
     * 根据指定的ID更新员工信息
     *
     * @param employee 指定更新员工的信息
     * @return Result响应对象
     */
    Result<String> update(Employee employee);

    /**
     * 根据指定ID获取员工信息
     *
     * @param id 指定的ID
     * @return 指定ID员工的信息
     */
    Result<Employee> getEmpById(Long id);
}

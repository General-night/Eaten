package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.Employee;
import com.it.exception.ConsumerException;
import com.it.mapper.EmployeeMapper;
import com.it.service.EmployeeService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:58
 * @description
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    /**
     * 员工登录业务
     *
     * @param employee 员工用户名和密码信息
     * @return 响应对象
     */
    @Override
    public Result<Employee> login(Employee employee) {

        // 1、获取登录密码并MD5加密
        String pwd = employee.getPassword();
        if (StringUtils.isBlank(pwd)) {
            return Result.error("密码不能为空");
        }
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());

        // 2、根据用户名查询员工信息
        Employee emp = getOne(
                new LambdaQueryWrapper<Employee>().eq(Employee::getUsername, employee.getUsername())
        );
        if (ObjectUtils.equals(emp, null)) {
            return Result.error("该用户名不存在");
        }

        // 3、比较密码是否正确
        if (!StringUtils.equals(pwd, emp.getPassword())) {
            return Result.error("密码不正确");
        }

        // 4、校验员工状态
        if (emp.getStatus() != 1) {
            return Result.error("账号已禁用，请及时联系管理员");
        }

        // 5、返回登录成功响应对象
        return Result.success(emp);
    }

    /**
     * 添加员工信息
     *
     * @param employee 员工信息
     * @return 是否添加成功
     */
    @Override
    public Result<String> addEmployee(Employee employee) {

        // 判断员工账号是否存在
        String username = employee.getUsername();
        Employee emp = getOne(
                new LambdaQueryWrapper<Employee>().eq(Employee::getUsername, username)
        );
        if (!ObjectUtils.equals(emp, null)) {
            throw new ConsumerException(username + " 账号已存在");
        }

        // 设置默认密码
        // 获取身份证后六位充当默认密码，并用 MD5 进行加密
        String idCard = employee.getIdNumber();
        String pwd = DigestUtils.md5DigestAsHex(idCard.substring(12).getBytes());
        employee.setPassword(pwd);

        // 添加员工
        save(employee);

        return Result.success(null);
    }

    /**
     * 初始化或根据员工昵称模糊查询，并对结果集进行分页处理
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     员工昵称
     * @return 分页器
     */
    @Override
    public IPage<Employee> page(Integer page, Integer pageSize, String name) {

        // 创建分页器对象
        IPage<Employee> pageInfo = new Page<>(page, pageSize);

        // 创建条件查询对象
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);

        // 设置排序规则
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行分页查询
        page(pageInfo, queryWrapper);

        return pageInfo;
    }

    /**
     * 根据指定的ID更新员工信息
     *
     * @param employee 指定更新员工的信息
     * @return Result响应对象
     */
    @Override
    public Result<String> update(Employee employee) {

        updateById(employee);

        return Result.success(null);
    }
}

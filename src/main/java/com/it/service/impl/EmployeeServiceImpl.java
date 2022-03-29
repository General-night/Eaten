package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.Employee;
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
     * @param empId    当前登录者 ID
     * @return 是否添加成功
     */
    @Override
    public Result<String> addEmployee(Employee employee, Long empId) {

        // 设置默认密码
        // 获取身份证后六位充当默认密码，并用 MD5 进行加密
        String idCard = employee.getIdNumber();
        String pwd = DigestUtils.md5DigestAsHex(idCard.substring(12).getBytes());
        employee.setPassword(pwd);

        // 设置创建者和修改者
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        // 添加员工
        save(employee);

        return Result.success(null);
    }
}

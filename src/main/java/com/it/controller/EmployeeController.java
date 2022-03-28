package com.it.controller;

import com.it.common.Result;
import com.it.entity.Employee;
import com.it.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:56
 * @description 员工控制层
 */

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public Result<Employee> login(HttpSession session, @RequestBody Employee employee) {
        Result<Employee> res = employeeService.login(employee);

        // 如果登录成功，设置session
        if (res.getCode() == 1) {
            session.setAttribute("empId", res.getData().getId());
        }

        return res;
    }

    @PostMapping("logout")
    public Result<String> logout(HttpSession session) {
        session.invalidate();

        return Result.success("退出成功");
    }
}

package com.it.controller;

import com.it.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void login() {
        System.out.println("登录成功！");
    }
}

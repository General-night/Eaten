package com.it.controller;

import com.it.common.Result;
import com.it.entity.Employee;
import com.it.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param session  session信息
     * @param employee 员工登录信息
     * @return Result响应对象
     */
    @PostMapping("login")
    public Result<Employee> login(HttpSession session, @RequestBody Employee employee) {

        log.info("登录，入参：{}", employee);

        Result<Employee> res = employeeService.login(employee);

        // 如果登录成功，设置session
        if (res.getCode() == 1) {
            session.setAttribute("empId", res.getData().getId());
        }

        log.info("登录，出参：{}", res);

        return res;
    }

    /**
     * 员工登出
     *
     * @param session session信息
     * @return Result响应对象
     */
    @PostMapping("logout")
    public Result<String> logout(HttpSession session) {

        log.info("登出，入参：{}", session);

        session.invalidate();

        Result<String> res = Result.success("退出成功");

        log.info("登出，出参：{}", res);

        return res;
    }

    /**
     * 添加员工
     *
     * @param employee 员工信息
     * @param session  当前登录者 ID
     * @return 是否添加成功
     */
    @PostMapping
    public Result<String> addEmployee(@RequestBody Employee employee, HttpSession session) {

        log.info("添加员工，入参：{}", employee);

        Long empId = (Long) session.getAttribute("empId");
        Result<String> res = employeeService.addEmployee(employee, empId);

        log.info("添加员工，出参：{}", res);

        return res;
    }
}

package com.it.controller.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.entity.backend.Employee;
import com.it.service.backend.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 员工控制层
 *
 * @author GeneralNight
 * @date 2022/3/27 12:56
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
     * @return 是否添加成功
     */
    @PostMapping
    public Result<String> addEmployee(@RequestBody Employee employee) {

        log.info("添加员工，入参：{}", employee);

        Result<String> res = employeeService.addEmployee(employee);

        log.info("添加员工，出参：{}", res);

        return res;
    }

    /**
     * 初始化或根据员工昵称模糊查询，并对结果集进行分页处理
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param name     员工昵称
     * @return 分页器
     */
    @GetMapping("page")
    public Result<IPage<Employee>> page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        String name) {

        log.info("分页查询，入参：page={}，pageSize={}，name={}", page, pageSize, name);

        IPage<Employee> pageInfo = employeeService.page(page, pageSize, name);

        log.info("分页查询，出参：pageInfo={}", JSON.toJSONString(pageInfo));

        return Result.success(pageInfo);
    }

    /**
     * 根据指定ID修改员工信息
     *
     * @param employee 指定更新员工的信息
     * @return Result响应对象
     */
    @PutMapping
    public Result<String> update(@RequestBody Employee employee) {
        log.info("更新员工信息，入参：{}", employee);

        Result<String> res = employeeService.update(employee);

        log.info("更新员工信息，出参：{}", JSON.toJSONString(res));

        return res;
    }

    /**
     * 根据指定ID获取员工信息
     *
     * @param id 指定的ID
     * @return 指定ID员工的信息
     */
    @GetMapping("{id}")
    public Result<Employee> getEmpById(@PathVariable Long id) {

        log.info("数据回显，入参：{}", id);

        Result<Employee> res = employeeService.getEmpById(id);

        log.info("数据回显，出参：{}", JSON.toJSONString(res));

        return res;
    }
}

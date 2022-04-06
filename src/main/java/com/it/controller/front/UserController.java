package com.it.controller.front;

import com.alibaba.fastjson.JSON;
import com.it.common.Result;
import com.it.entity.front.User;
import com.it.service.front.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author NightGeneral
 * @date 2022/4/6 16:22
 */

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 发送手机验证码
     *
     * @param param 手机号码
     * @return 是否发送成功
     */
    @PostMapping("sendMsg")
    public Result<String> sendMsg(@RequestBody Map<String, String> param) {

        log.info("客户端-发送短信，入参：phoneNumber={}", param);

        Result<String> res = service.sendMsg(param.get("phone"));

        log.info("客户端-发送短信，出参：{}", JSON.toJSONString(res));

        return res;
    }


    /**
     * 根据手机号和验证码登录
     *
     * @param params 手机号和验证码
     * @return 是否登录成功
     */
    @PostMapping("login")
    public Result<User> login(@RequestBody Map<String, String> params, HttpSession session) {

        log.info("客户端-登录，入参：{}", params);

        Result<User> res = service.login(params);

        // 如果登录成功，用session记录登录状态，用于登录校验
        User user = res.getData();
        if (!ObjectUtils.equals(user, null)) {
            session.setAttribute("user", user.getId());
        }

        log.info("客户端-登录，入参：{}", JSON.toJSONString(res));

        return res;
    }
}

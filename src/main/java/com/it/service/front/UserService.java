package com.it.service.front;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.front.User;

import java.util.Map;

/**
 * @author NightGeneral
 * @date 2022/4/6 16:17
 */

public interface UserService extends IService<User> {

    /**
     * 发送手机验证码
     *
     * @param phone 手机号码
     * @return 是否发送成功
     */
    Result<String> sendMsg(String phone);


    /**
     * 根据手机号和验证码登录
     *
     * @param params 手机号和验证码
     * @return 是否登录成功
     */
    Result<User> login(Map<String, String> params);
}

package com.it.service.impl.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.front.User;
import com.it.exception.ConsumerException;
import com.it.mapper.front.UserMapper;
import com.it.service.front.UserService;
import com.it.utils.SMSUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

/**
 * @author NightGeneral
 * @date 2022/4/6 16:18
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送手机验证码
     *
     * @param phone 手机号码
     * @return 是否发送成功
     */
    @Override
    public Result<String> sendMsg(String phone) {

        // 设置一个存储手机验证码的key
        String codeKey = "CHECK_" + phone;

        // 如果redis中存在当前的key，说明验证码还未失效
        String value = redisTemplate.opsForValue().get(codeKey);
        if (StringUtils.isNotBlank(value)) {
            throw new ConsumerException("验证码还未失效");
        }

        // 1、发送验证码并返回对应的验证码
        String code = SMSUtils.sendMessage(phone);

        // 2、将验证码存储到redis中并设置过期时间
        redisTemplate.opsForValue().set(codeKey, code, Duration.ofMinutes(5));

        return Result.success("发送成功");
    }

    /**
     * 根据手机号和验证码登录
     *
     * @param params 手机号和验证码
     * @return 是否登录成功
     */
    @Override
    public Result<User> login(Map<String, String> params) {

        // 1、获取手机号码和验证码
        String phone = params.get("phone");
        String code = params.get("code");

        // 2、获取redis中的key
        String codeKey = "CHECK" + phone;
        String value = redisTemplate.opsForValue().get(codeKey);
        if (StringUtils.isNotBlank(value)) {
            return Result.error("验证码无效");
        } else if (StringUtils.equals(code, value)) {
            return Result.error("验证码错误");
        }

        // 如果验证码通过
        // 3、验证码已经使用了，就要删除redis中的数据
        redisTemplate.delete(codeKey);

        // 4、查询当前手机号是否存在
        User user = getOne(
                new LambdaQueryWrapper<User>()
                        .eq(StringUtils.isNotBlank(phone), User::getPhone, phone)
        );

        // 如果当前用户不存在要进行注册
        if (ObjectUtils.equals(user, null)) {
            User u = new User();
            u.setPhone(phone);
            u.setStatus(1);
            save(u);
        }

        return Result.success(user);
    }
}

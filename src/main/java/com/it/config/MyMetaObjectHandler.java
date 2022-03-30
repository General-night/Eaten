package com.it.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author GeneralNight
 * @date 2022/3/29 18:10
 * @description
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    HttpSession session;

    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建、修改时间
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        // 自动填充创建、修改者
        metaObject.setValue("createUser", session.getAttribute("empId"));
        metaObject.setValue("updateUser", session.getAttribute("empId"));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充修改时间
        metaObject.setValue("updateTime", LocalDateTime.now());

        //自动填充修改者
        metaObject.setValue("updateUser", session.getAttribute("empId"));
    }
}

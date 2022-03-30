package com.it.common;

import com.it.exception.ConsumerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通用异常处理器
 *
 * @author GeneralNight
 * @date 2022/3/30 16:35
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理方法
     *
     * @param ce 自定义异常对象
     * @return 响应结果
     */
    @ExceptionHandler(ConsumerException.class)
    public Result<String> consumerExceptionHandler(ConsumerException ce) {
        log.error("异常：{}", ce.getMessage());
        return Result.error(ce.getMessage());
    }

    /**
     * 最终异常处理方法
     *
     * @param e 异常对象
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error("异常：{}", e.getMessage());
        return Result.error("未知异常");
    }
}

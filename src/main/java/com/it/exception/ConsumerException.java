package com.it.exception;

/**
 * 自定义异常类
 *
 * @author GeneralNight
 * @date 2022/3/30 16:37
 */
public class ConsumerException extends RuntimeException {

    /**
     * @param message 异常信息
     */
    public ConsumerException(String message) {
        super(message);
    }
}

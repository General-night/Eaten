package com.it.config;

import com.it.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author GeneralNight
 * @date 2022/3/27 12:35
 * @description
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源访问映射
     *
     * @param registry 注册器
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 拓展 SpringMvc 消息转换器
     *
     * @param converters SpringMvc 内置消息转换器集合
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        // 添加自定义消息类型转换器，指定优先使用，不使用默认的jackson消息类型转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 将上面自定义的对象转换器追加到 SpringMvc 消息转换器集合中
        // （要指定添加到第一位，转换器是有顺序不然会不生效）
        converters.add(0, messageConverter);
    }
}

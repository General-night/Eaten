package com.it.filter;

import com.alibaba.fastjson.JSON;
import com.it.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author GeneralNight
 * @date 2022/3/28 9:58
 * @description
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * 路径匹配器，支持正则匹配
     */
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取 URI
        String uri = request.getRequestURI();

        log.info("拦截到请求：{}", uri);

        // 定义非校验路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };

        // 2、判断 URI 是否需要做登录校验
        boolean check = check(urls, uri);

        // 3、如果不需要，则直接放行
        if (check) {
            log.info("{} 不需要做登录校验", uri);
            filterChain.doFilter(request, response);
            return;
        }

        // 4-1、如果需要，判断是否登录（后台）
        if (!ObjectUtils.equals(request.getSession().getAttribute("empId"), null)) {
            log.info("{} 用户已登录", request.getSession().getAttribute("empId"));
            filterChain.doFilter(request, response);
            return;
        }

        // 4-2、如果需要，判断是否登录（客户端）
        if (!ObjectUtils.equals(request.getSession().getAttribute("user"), null)) {
            log.info("{} 用户已登录", request.getSession().getAttribute("user"));
            filterChain.doFilter(request, response);
            return;
        }

        // 5、如果未登录则返回未登录结果，通过输出流方式向客户端页面做响应
        log.info("用户未登录！");
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }

    /**
     * 判断 URI 是否需要做登录校验
     *
     * @param uri  被检测的URI
     * @param urls 非需校验URI数组
     * @return true：不需要校验，false：需要校验
     */
    private boolean check(String[] urls, String uri) {
        for (String url : urls) {
            // 注意：url 和 uri 顺序不能变
            if (PATH_MATCHER.match(url, uri)) {
                return true;
            }
        }

        return false;
    }
}

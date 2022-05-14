package com.cooler.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public class EncodingFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("进入过滤字符编码的过滤器");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        chain.doFilter(req,resp);
    }


}

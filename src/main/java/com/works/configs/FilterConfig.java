package com.works.configs;

import com.works.entities.Customer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class FilterConfig implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String url = req.getRequestURI();
        String freeUrls[] = {"/","/customerLogin"};
        boolean loginStatus = true;
        for(String item : freeUrls){
            if(url.equals(item)){
                loginStatus = false;
                break;
            }
        }
        if(loginStatus) {
            boolean status = req.getSession().getAttribute("customer") == null;
            if (status) {
                res.sendRedirect("/");
            } else {
                Customer c = (Customer) req.getSession().getAttribute("customer");
                req.setAttribute("customer",c);
                filterChain.doFilter(req, res);
            }
        }else{
            filterChain.doFilter(req,res);
        }
    }
}

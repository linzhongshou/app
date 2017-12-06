package cn.linzs.app.common.filter;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.utils.JwtUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author linzs
 * @Date 2017-12-04 9:32
 * @Description 判断JWT Token，如果不对将重定向到登录页面
 */
@WebFilter(filterName = "jwtFilter", urlPatterns = "/api/*")
@Order(1)
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            // 没有Token，重定向到登录页面
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(new ReturnResult(ReturnResult.BusinessCode.REDIRECT_TO_LOGIN_PAGE, null)));
            writer.flush();
            return ;
        }

        final String token = authHeader;
        try {
            final Claims claims = JwtUtil.getClaimsFromToken(token);
            request.setAttribute("claims", claims);
        } catch (ExpiredJwtException e) {
            // Token过期，重定向到登录页面
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(new ReturnResult(ReturnResult.BusinessCode.REDIRECT_TO_LOGIN_PAGE, null)));
            writer.flush();
            return ;
        } catch (Exception e) {
            throw new ServletException("Invalid token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

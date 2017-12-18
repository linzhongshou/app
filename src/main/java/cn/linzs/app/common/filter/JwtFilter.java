package cn.linzs.app.common.filter;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.exception.ExpiredTokenException;
import cn.linzs.app.common.utils.token.ITokenManager;
import cn.linzs.app.common.utils.token.JwtUtil;
import cn.linzs.app.common.utils.token.model.TokenModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author linzs
 * @Date 2017-12-04 9:32
 * @Description 判断JWT Token，如果不对将重定向到登录页面
 */
@WebFilter(filterName = "jwtFilter", urlPatterns = "/api/*")
@Order(1)
public class JwtFilter implements Filter {

    @Autowired
    ITokenManager tokenManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 预检的请求直接跳过
        if(request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            return ;
        }

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            // 没有Token，重定向到登录页面
            response.setStatus(ReturnResult.HttpCode._401);
            return ;
        }

        final String token = authHeader;
        try {
            tokenManager.refreshToken(token);
            final TokenModel tokenModel = tokenManager.getTokenModel(token);
            request.setAttribute("tokenModel", tokenModel);
        } catch (ExpiredTokenException e) {
            // Token过期，重定向到登录页面
            response.setStatus(ReturnResult.HttpCode._401);
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

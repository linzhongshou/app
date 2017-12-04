package cn.linzs.app.common.filter;

import cn.linzs.app.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author linzs
 * @Date 2017-12-04 9:32
 * @Description
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

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            throw new ServletException("Missing Authorization header.");
        }

        final String token = authHeader;

        try {
            final Claims claims = JwtUtil.getClaimsFromToken(token);
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            throw new ServletException("Invalid token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

package cn.linzs.app.common.filter;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.exception.ExpiredTokenException;
import cn.linzs.app.common.utils.ShiroUtil;
import cn.linzs.app.common.utils.token.ITokenManager;
import cn.linzs.app.common.utils.token.JwtUtil;
import cn.linzs.app.common.utils.token.model.TokenModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 预检的请求直接跳过
        if(request.getMethod().equals("OPTIONS")) {
            response.setStatus(ReturnResult.HttpCode._200);
            return true;
        }

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            // 没有Token，重定向到登录页面
            response.setStatus(ReturnResult.HttpCode._401);
            return false;
        }

        final String token = authHeader.replace("Bearer ", "");
        try {
            Claims claims = JwtUtil.getClaimsFromToken(token);
            request.setAttribute("claims", claims);

            UsernamePasswordToken shiroToken = new UsernamePasswordToken();
            shiroToken.setUsername(claims.get("userId").toString());
            shiroToken.setPassword(token.toCharArray());
            ShiroUtil.getSubject().login(shiroToken);
        } catch (ExpiredJwtException e) {
            // Token过期，重定向到登录页面
            response.setStatus(ReturnResult.HttpCode._401);
            return false;
        } catch (Exception e) {
            throw new ServletException("Invalid token.");
        }

        return true;
    }

}

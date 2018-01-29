package cn.linzs.app.common.filter;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.utils.ShiroUtil;
import cn.linzs.app.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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

        String[] tokens = authHeader.split("refreshToken");
        final String accessToken = tokens[0].replace("Bearer ", "").trim();
        final String refreshToken = tokens[1].trim();
        try {
            parseToken(accessToken, request);
        } catch (ExpiredJwtException e) {
            if(refreshToken != null) { // 如果有refreshToken且没过期，则颁发新的Token给用户
                try {
                    Claims claims = parseToken(refreshToken, request);
                    Map<String, Object> claimsMap = new HashMap<>();
                    claimsMap.put("userId", claims.get("userId"));
                    response.setHeader("access-token", JwtUtil.generateToken(claimsMap, 30));
                    response.setHeader("refresh-token", JwtUtil.generateToken(claimsMap, 60));
                    return true;
                } catch (Exception ex) { }
            }

            // Token过期，重定向到登录页面
            response.setStatus(ReturnResult.HttpCode._401);
            return false;
        } catch (Exception e) {
            throw new ServletException("Invalid token.");
        }

        return true;
    }

    private Claims parseToken(String token, HttpServletRequest request) throws ExpiredJwtException, Exception {
        Claims claims = JwtUtil.getClaimsFromToken(token);
        request.setAttribute("claims", claims);

        UsernamePasswordToken shiroToken = new UsernamePasswordToken();
        shiroToken.setUsername(claims.get("userId").toString());
        shiroToken.setPassword(token.toCharArray());
        ShiroUtil.getSubject().login(shiroToken);

        return claims;
    }

}

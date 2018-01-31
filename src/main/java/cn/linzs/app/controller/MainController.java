package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.exception.ExpiredTokenException;
import cn.linzs.app.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author linzs
 * @Date 2018-01-29 9:49
 * @Description
 */
@RestController
public class MainController {

    @RequestMapping(value = "/api/refreshToken")
    public ReturnResult refreshToken(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult result = null;
        String refreshToken = request.getParameter("refreshToken");
        if (refreshToken != null && !"".equals(refreshToken)) {
            try {
                Claims claims = JwtUtil.getClaimsFromToken(refreshToken);
                if (claims != null) {
                    Map<String, Object> tokenMap = new HashMap<>();
                    Map<String, Object> claimsMap = new HashMap<>();
                    claimsMap.put("userId", claims.get("userId"));
                    tokenMap.put("accessToken", JwtUtil.generateToken(claimsMap, 30));
                    tokenMap.put("refreshToken", JwtUtil.generateToken(claimsMap, 60));
                    result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, tokenMap);
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new ExpiredTokenException("refresh token expired.", e);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return result;
    }
}

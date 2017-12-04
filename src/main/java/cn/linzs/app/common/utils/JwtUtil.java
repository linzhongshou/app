package cn.linzs.app.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * @Author linzs
 * @Date 2017-12-01 16:20
 * @Description JWT 工具
 */
public final class JwtUtil {

    private final static Logger logger = Logger.getLogger(JwtUtil.class);

    private final static String secret = "JLKjslkdf92302=234kadf";

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.addMinutes(new Date(), 30))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
        } catch (Exception e) {
            logger.error("Get claims from token error. error information: " + e.getLocalizedMessage());
        }

        return claims;
    }

}

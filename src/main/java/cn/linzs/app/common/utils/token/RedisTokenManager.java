package cn.linzs.app.common.utils.token;

import cn.linzs.app.common.exception.ExpiredTokenException;
import cn.linzs.app.common.utils.token.model.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author linzs
 * @Date 2017-12-18 11:30
 * @Description ITokenManager的Redis实现
 */
@Component(value = "redisTokenManager")
public class RedisTokenManager implements ITokenManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${token.expiretime}")
    private long expiretime;

    @Override
    public TokenModel generateToken(Map<String, Object> dataMap) {
        String token = JwtUtil.generateToken(dataMap);

        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(token);
        tokenModel.setDataMap(dataMap);
        tokenModel.setCreateTime(new Date().getTime());
        tokenModel.setExpire(expiretime);
        // 写入Redis
        redisTemplate.opsForValue().set(token, tokenModel, expiretime, TimeUnit.SECONDS);

        return tokenModel;
    }

    @Override
    public TokenModel getTokenModel(String token) {
        TokenModel tokenModel;
        Object redisData = redisTemplate.opsForValue().get(token);
        if(redisData != null) {
            tokenModel = (TokenModel) redisData;
        } else {
            throw new ExpiredTokenException("token expired.");
        }
        return tokenModel;
    }

    @Override
    public boolean expired(String token) {
        Object redisData = redisTemplate.opsForValue().get(token);
        if(redisData != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void refreshToken(String token) {
        redisTemplate.expire(token, expiretime, TimeUnit.SECONDS);
    }
}

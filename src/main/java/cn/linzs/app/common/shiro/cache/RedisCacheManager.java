package cn.linzs.app.common.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linzs
 * @Date 2018-01-26 16:47
 * @Description
 */
public class RedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    private final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private RedisTemplate redisTemplate;

    public RedisCacheManager() {}

    public RedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cache cache = cacheMap.get(s);
        if(cache == null) {
            cache = new RedisCache(redisTemplate);
            cacheMap.put(s, cache);
        }

        return cache;
    }
}

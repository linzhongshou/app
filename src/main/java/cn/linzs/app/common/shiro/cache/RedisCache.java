package cn.linzs.app.common.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * @Author linzs
 * @Date 2018-01-26 15:35
 * @Description
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate<K, V> redisTemplate;
    private HashOperations<String, K, V> hashOperations;
    private final static String REDIS_KEY = "SHIRO_KEY_COMMMMMM";

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public V get(K k) throws CacheException {
        try {
            if(k != null) {
                return hashOperations.get(REDIS_KEY, k);
            } else {
                return null;
            }
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        try {
            hashOperations.put(REDIS_KEY, k, v);
            return v;
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V remove(K k) throws CacheException {
        try {
            V v = get(k);
            hashOperations.delete(REDIS_KEY, k);
            return v;
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            hashOperations.delete(REDIS_KEY, hashOperations.keys(REDIS_KEY));
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public int size() {
        return hashOperations.keys(REDIS_KEY).size();
    }

    @Override
    public Set<K> keys() {
        return hashOperations.keys(REDIS_KEY);
    }

    @Override
    public Collection<V> values() {
        return hashOperations.entries(REDIS_KEY).values();
    }
}

package com.ryx.credit.cms.shiro.cache;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;

/**
 * 作者：cx
 * 时间：2019/3/26
 * 描述：
 */
public class ShiroRedisCacheManager implements CacheManager, Destroyable {

    private static final Logger logger = LogManager.getLogger(ShiroSpringCacheManager.class);

    private org.springframework.cache.CacheManager cacheManager;

    @Override
    public <k,v> Cache<k,v> getCache(String name) throws CacheException {
        RedisCache redisCache = (RedisCache)cacheManager.getCache(name);
        ShiroRedisCache shiroRedisCache = new ShiroRedisCache(redisCache);
        return shiroRedisCache;
    }

    @Override
    public void destroy() throws Exception {
        cacheManager = null;
    }


    public org.springframework.cache.CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}

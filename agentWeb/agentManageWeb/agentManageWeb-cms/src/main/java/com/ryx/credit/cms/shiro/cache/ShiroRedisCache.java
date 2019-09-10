package com.ryx.credit.cms.shiro.cache;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.lang.String;
import java.lang.Object;

/**
 * 作者：cx
 * 时间：2019/3/26
 * 描述：
 */
public class ShiroRedisCache<k,v> implements org.apache.shiro.cache.Cache<k,v>  {

    private static final Logger logger = LogManager.getLogger(ShiroRedisCache.class);

    private RedisCache redisCache_local;

    public ShiroRedisCache(RedisCache redisCache ) {
        redisCache_local = redisCache;
    }


    @Override
    public v get(k key) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.debug("Getting object from cache [" + this.redisCache_local.getName() + "] for key [" + key + "]key type:" + key.getClass());
        }
        Cache.ValueWrapper valueWrapper = redisCache_local.get(key);
        if (valueWrapper == null) {
            if (logger.isTraceEnabled()) {
                logger.trace("Element for [" + key + "] is null.");
            }
            return null;
        }
        return (v) valueWrapper.get();
    }

    @Override
    public v put(k key, v value) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Putting object in cache [" + this.redisCache_local.getName() + "] for key [" + key + "]key type:" + key.getClass());
        }
        Object previous = get(key);
        redisCache_local.put(key, value);
        return (v)previous;
    }

    @Override
    public v remove(k key) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Removing object from cache [" + this.redisCache_local.getName() + "] for key [" + key + "]key type:" + key.getClass());
        }
        Object previous = get(key);
        redisCache_local.evict(key);
        return (v)previous;
    }



    @Override
    public void clear() throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Clearing all objects from cache [" + this.redisCache_local.getName() + "]");
        }
        redisCache_local.clear();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<k> keys() {
        return Collections.emptySet();
    }

    @Override
    public Collection<v> values() {
        return Collections.emptySet();
    }


}

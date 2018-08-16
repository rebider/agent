package com.ryx.credit.common.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.SessionCallback;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

public class RedisService {

	@Autowired
    protected RedisTemplate<String, String> redisTemplate;

	private Logger logger = Logger.getLogger(this.getClass());

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
	 * 放置key value 失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
    public void setValue(final String key,final String value,final Long seconds) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(redisTemplate.getStringSerializer().serialize(key),
                		seconds, redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    /**
     * 是否存在key value 失效时间
     * @param key
     * @param value
     */
    public String setNx(final String key,final String value) {
        try {
            return (String) redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean b = connection.setNX(redisTemplate.getStringSerializer().serialize(key), redisTemplate.getStringSerializer().serialize(value));
                    return b.toString();
                }
            });
        } catch (Exception e) {
            logger.error("setNx error",e);
        }
        return null;
    }

    /**
     * 失效时间
     * @param key
     */
    public Long ttl(final String key) {
        return (Long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long b = connection.ttl(redisTemplate.getStringSerializer().serialize(key));
                return b;
            }
        });
    }


    /**
     * 获取value
     * @param key
     * @return
     */
    public String getValue(final String key) {
    	
        return redisTemplate.execute(new RedisCallback<String>() {
        	String value = "";
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] byteKey = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(byteKey)) {
                    byte[] bytes = connection.get(byteKey);
                    if(bytes.length>0){
                    	value = redisTemplate.getStringSerializer().deserialize(bytes);
                        return value;
                    }
                }
                return value;
            }
        });
    }
    
    /**
     * 返回对象泛型
     * @param key
     * @param cla
     * @return
     */
    public <T> T getObject(final String key,Class<?> cla) {
    	String value = this.getValue(key);
    	if(StringUtils.isEmpty(value)){
    		return null;
    	}
    	try {
    		@SuppressWarnings("unchecked")
			T t = (T) JSON.parseObject(value, cla);
        	return t;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
    }
    
    /**
     * 返回对象list 泛型
     * @param key
     * @param cla
     * @return
     */
    public List<?> getList(final String key,Class<?> cla) {
    	String value = this.getValue(key);
    	if(StringUtils.isEmpty(value)){
    		return null;
    	}
    	try {
    		List<?> tlist = JSON.parseArray(value, cla);
        	return tlist;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
    }
    
    /**  
     * 删除 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }
  
    /**  
     * 删除模糊匹配上的所有缓存
     * 比如redisKey是由时间加随机数生成，deleteByCondition("session")则删除所有包含session信息的缓存
     * @param key 
     */  
    public void deleteByCondition(String condition) {  
    	Set<String> keys = redisTemplate.keys(condition + "*");
        redisTemplate.delete(keys);
    }
    
    /** 
     * 删除多个 
     * @param keys 
     */  
    public void delete(List<String> keys) {
    	redisTemplate.delete(keys);
    }

    /**
     * 加锁
     * @param locaName  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locaName,
                                  long acquireTimeout, long timeout) {
        String retIdentifier =null;
        try {
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (setNx(lockKey, identifier).equals("true")) {
                    redisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (ttl(lockKey) == -1) {
                    redisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            logger.error("lockWithTimeout error",e);
        }
        return retIdentifier;
    }


    /**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        final String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            while (true) {
                // 监视lock，准备开始事务
                redisTemplate.watch(lockKey);

                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(getValue(lockKey))) {
                    SessionCallback sessionCallback = new SessionCallback() {
                        @Override
                            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                            redisOperations.multi();
                            delete(lockKey);
                            List<Object> results = redisTemplate.exec();
                            return results;
                        }
                    };
                    Object o = redisTemplate.execute(sessionCallback);
                    retFlag = true;
                    redisTemplate.unwatch();
                }
                break;
            }
        } catch (Exception e) {
            logger.error("releaseLock error",e);
        }
        return retFlag;
    }


    /**
     * 添加一个锁
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean versionLock(String key,String value,long timeout){
        try {
            setValue(key, value, timeout);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean releaseVersionLock(String key,String value){
        try {
            String lockaV =  getValue(key);
            if(StringUtils.isEmpty(lockaV) || !lockaV.equals(value)){
                return false;
            }
            delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param key
     * @param fields
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hGetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }






}

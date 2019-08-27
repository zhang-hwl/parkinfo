package com.parkinfo.cache;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author cnyuchu@gmail.com
 * @create 2019-06-24 17:58
 **/
@Component
public class CacheService {

    public static final String WEB_CACHE_PREFIX = "CACHE:WEB:";

    public static final String APP_CACHE_PREFIX = "CACHE:APP:";

    public static final String DOWNLOAD_AUTH_CACHE_PREFIX = "CACHE:DOWNLOAD:";

    public static final String VALIDATE_CODE = "VALIDATE_CODE:";

    public static final String INTEGRAL_SETTINGS = "CACHE:APP:INTEGRAL_SETTINGS:";

    public static final String CULLING_HOME = "CACHE:CULLING_HOME";

    public static final String CACHE = "CACHE:";

    public static final String KNOWLEDGE_CAPSULE = "KNOWLEDGE_CAPSULE";

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

//    public void writeIntegralSetting(GetIntegralWay getIntegralWay){
//        redisTemplate.opsForValue().set(INTEGRAL_SETTINGS+getIntegralWay.getIntegralPolicyType().toString(),getIntegralWay);
//    }
//
//    public GetIntegralWay getIntegralSetting(IntegralPolicyType integralPolicyType){
//        return (GetIntegralWay) redisTemplate.opsForValue().get(INTEGRAL_SETTINGS + integralPolicyType.toString());
//    }
    /**
     * 设置web管理端缓存
     * @param key
     * @param object
     */
    public void setWebCache(String key,Serializable object) {
        redisTemplate.opsForValue().set(WEB_CACHE_PREFIX+key, object);
    }

    /**
     * 设置首页推荐缓存
     * @param object
     * @param object
     */
    public void setCullingHome(Serializable object) {
        redisTemplate.opsForValue().set(CULLING_HOME, object);
    }

    /**
     * 删除首页推荐缓存
     */
    public void delCullingHome() {
        redisTemplate.delete(CULLING_HOME);
    }

    /**
     * 获取首页推荐缓存
     * @return
     */
    public Serializable getCullingHome() {
       return redisTemplate.opsForValue().get(CULLING_HOME);
    }

    /**
     * 删除web管理端缓存
     * @param key
     */
    public void removeWebCache(String key){
        redisTemplate.delete(WEB_CACHE_PREFIX+key);
    }

    /**
     * 获取web管理端缓存
     * @param key
     */
    public Serializable getWebCache(String key){
        return redisTemplate.opsForValue().get(WEB_CACHE_PREFIX+key);
    }

    /**
     * 设置app客户端缓存
     * @param key
     */
    public void setAPPCache(String key,Serializable object) {
        redisTemplate.opsForValue().set(APP_CACHE_PREFIX+key, object);
    }

    /**
     * 设置app客户端缓存
     * @param key
     */
    public void setAPPCache(String key, Serializable object, Integer time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(APP_CACHE_PREFIX+key, object,time,timeUnit);
    }

    /**
     * 删除app客户端缓存
     * @param key
     */
    public void removeAPPCache(String key){
        redisTemplate.delete(APP_CACHE_PREFIX+key);
    }

    /**
     * 获取app客户端缓存
     * @param key
     */
    public Serializable getAPPCache(String key){
        return redisTemplate.opsForValue().get(APP_CACHE_PREFIX+key);
    }

    /**
     * 设置自定义缓存
     * @param key
     * @param object
     * @param prefix 前缀
     */
    public void setCache(String key,Serializable object,String prefix) {
        redisTemplate.opsForValue().set(prefix+key, object);
    }

    /**
     * 删除自定义缓存
     * @param key
     * @param prefix 前缀
     */
    public void removeCache(String key,String prefix){
        redisTemplate.delete(prefix+key);
    }

    /**
     * 获取自定义缓存
     * @param key
     * @param prefix 前缀
     */
    public Serializable getCache(String key,String prefix){
        return redisTemplate.opsForValue().get(prefix+key);
    }

}

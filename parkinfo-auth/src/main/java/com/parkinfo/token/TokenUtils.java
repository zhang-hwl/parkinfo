package com.parkinfo.token;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author cnyuchu@gmail.com
 * @date 2019/3/4 15:07
 */
@Component
@Slf4j
public class TokenUtils {
    private static final String TOKEN_PREFIX = "TOKEN:USER:";

    private static final String USER_SURVIVE = "SURVIVE:";

    private static final String PHONE = "PHONE:";

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

//    @Autowired
//    private AppUserRepository appUserRepository;


//    /**
//     * 根据token从redis获取用户
//     *
//     * @return
//     */
//    public AppUser getLoginUser(String token) {
//        final AppUser user = (AppUser) redisCacheTemplate.opsForValue().get(TOKEN_PREFIX + token);
//        if (user != null) {
//            if ((TOKEN_PREFIX + token).equals(redisCacheTemplate.opsForValue().get(USER_SURVIVE + user.getId()))) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据token从redis获取用户
//     *
//     * @return
//     */
//    public AppUser getLoginUser() {
//        try {
//            String token = SecurityUtils.getSubject().getPrincipal().toString();
//            return this.getLoginUser(token);
//        } catch (Exception e) {   //token不存在
//            return null;
//        }
//    }
//
//
//    public String getToken() {
//        String token = SecurityUtils.getSubject().getPrincipal().toString();
//        if (null == token) {
//            throw new NormalException("token获取失败");
//        }
//        return token;
//    }
//
//    /**
//     * 缓存验证码
//     */
//    public void saveSMSValidateCode(String phone, String code) {
//        redisCacheTemplate.opsForValue().set(PHONE + phone, code);
//        redisCacheTemplate.expire(PHONE + phone, 5, TimeUnit.MINUTES);
//    }
//
//    public String getSMSValidateCode(String phone) {
//        return (String) redisCacheTemplate.opsForValue().get(PHONE + phone);
//    }
//
//    /**
//     * 从数据库中查询
//     *
//     * @return
//     */
//    public AppUser getUserInfo() {
//        try {
//            String token = SecurityUtils.getSubject().getPrincipal().toString();
//            AppUser loginUser = this.getLoginUser(token);
//            return this.getUserInfo(loginUser.getId());
//        } catch (Exception e) {  //token不存在
//            return null;
//        }
//    }
//
//    /**
//     * 从数据库中查询
//     *
//     * @return
//     */
//    public AppUser getUserInfo(String userId) {
//        Optional<AppUser> adminUserOptional = appUserRepository.findById(userId);
//        return adminUserOptional.orElse(null);
//    }
//
//
//    /**
//     * 生成token
//     *
//     * @param appUser
//     * @return
//     */
//    public String generateTokeCode(AppUser appUser) {
//        Set<CourseCategory> courseCategories = appUser.getCategories();
//        Set<CourseCategory> newCategories = new HashSet<>();
//        courseCategories.forEach(courseCategory -> {
//            CourseCategory category = new CourseCategory();
//            category.setId(courseCategory.getId());
//            category.setName(category.getName());
//            category.setSort(category.getSort());
//            category.setIntro(category.getIntro());
//            newCategories.add(category);
//        });
//        appUser.setCategories(newCategories);
//        appUser.setQqUser(appUser.getQqUser());
//        appUser.setWeiboUser(appUser.getWeiboUser());
//        appUser.setWechatUser(appUser.getWechatUser());
//        String value = System.currentTimeMillis() + new Random().nextInt() + "";
//        //获取数据指纹，指纹是唯一的
//        try {
//            MessageDigest md = MessageDigest.getInstance("md5");
//            byte[] b = md.digest(value.getBytes());//产生数据的指纹
//            //Base64编码
//            BASE64Encoder be = new BASE64Encoder();
//            String key = be.encode(b);
//            redisCacheTemplate.opsForValue().set(TOKEN_PREFIX + key, appUser);
//            redisCacheTemplate.expire(TOKEN_PREFIX + key, 30, TimeUnit.DAYS);
//            redisCacheTemplate.opsForValue().set(USER_SURVIVE + appUser.getId(), TOKEN_PREFIX + key);  //存活的token更新
//            return key;
//        } catch (NoSuchAlgorithmException e) {
//            throw new NormalException("token生成失败");
//        }
//    }

    /**
     * 设置token过期
     */
    public void setExpired() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        redisCacheTemplate.delete(token);
    }
}

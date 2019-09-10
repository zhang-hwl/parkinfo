package com.parkinfo.token;

import com.google.common.collect.Lists;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.dto.ParkUserPermissionDTO;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private ParkInfoRepository parkInfoRepository;


    /**
     * 根据token从redis获取用户
     *
     * @return
     */
    public ParkUserDTO getLoginUserDTO(String token) {
        final ParkUserDTO user = (ParkUserDTO) redisCacheTemplate.opsForValue().get(TOKEN_PREFIX + token);
        if (user != null) {
            if ((TOKEN_PREFIX + token).equals(redisCacheTemplate.opsForValue().get(USER_SURVIVE + user.getId()))) {
                return user;
            }
        }
        return null;
    }

    /**
     * 根据token从redis获取用户
     *
     * @return
     */
    public ParkUserDTO getLoginUserDTO() {
        try {
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            return this.getLoginUserDTO(token);
        } catch (Exception e) {   //token不存在
            return null;
        }
    }

    public ParkUser getLoginUser(){
        ParkUserDTO parkUserDTO = this.getLoginUserDTO();
        if (parkUserDTO!=null){
            return this.convertParkUserDTO(parkUserDTO);
        }
        return null;
    }

    public ParkInfo getCurrentParkInfo(){
        ParkUserDTO loginUserDTO = this.getLoginUserDTO();
        if (loginUserDTO != null){
            Optional<ParkInfo> parkInfoOptional = parkInfoRepository.findFirstByDeleteIsFalseAndAvailableIsTrueAndId(loginUserDTO.getCurrentParkId());
            if (parkInfoOptional.isPresent()){
                return parkInfoOptional.get();
            }
        }
        return null;
    }



    public String getToken() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        if (null == token) {
            throw new NormalException("token获取失败");
        }
        return token;
    }
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
    /**
     * 从数据库中查询
     *
     * @return
     */
    public ParkUserDTO getUserInfo() {
        try {
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            ParkUserDTO loginUser = this.getLoginUserDTO(token);
            return this.getUserInfo(loginUser.getId());
        } catch (Exception e) {  //token不存在
            return null;
        }
    }

    /**
     * 从数据库中查询
     *
     * @return
     */
    public ParkUserDTO getUserInfo(String userId) {
        Optional<ParkUser> parkUserOptional = parkUserRepository.findById(userId);
        if (!parkUserOptional.isPresent()){
            return null;
        }
        ParkUser parkUser = parkUserOptional.get();
        return this.convertParkUser(parkUser);
    }


    /**
     * 生成token
     *
     * @param parkUser
     * @return
     */
    public String generateTokeCode(ParkUser parkUser,String parkId) {
        ParkUserDTO parkUserDTO = this.convertParkUser(parkUser,parkId);
        String value = System.currentTimeMillis() + new Random().nextInt() + "";
        //获取数据指纹，指纹是唯一的
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] b = md.digest(value.getBytes());//产生数据的指纹
            //Base64编码
            BASE64Encoder be = new BASE64Encoder();
            String key = be.encode(b);
            redisCacheTemplate.opsForValue().set(TOKEN_PREFIX + key, parkUserDTO);
            redisCacheTemplate.expire(TOKEN_PREFIX + key, 6, TimeUnit.HOURS);
            redisCacheTemplate.opsForValue().set(USER_SURVIVE + parkUserDTO.getId(), TOKEN_PREFIX + key);  //存活的token更新
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new NormalException("token生成失败");
        }
    }

    /**
     * 设置token过期
     */
    public void setExpired() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        redisCacheTemplate.delete(token);
    }
    private ParkUserDTO convertParkUser(ParkUser parkUser,String parkId){
        ParkUserDTO parkUserDTO = this.convertParkUser(parkUser);
        parkUserDTO.setCurrentParkId(parkId);
        return parkUserDTO;
    }

    private ParkUserDTO convertParkUser(ParkUser parkUser){
        ParkUserDTO parkUserDTO = new ParkUserDTO();
        BeanUtils.copyProperties(parkUser,parkUserDTO);
        List<ParkUserPermissionDTO> permissionDTOList = Lists.newArrayList();
        List<String> roleList = Lists.newArrayList();
        if (parkUser.getRoles()!=null){
            parkUser.getRoles().forEach(parkRole -> {
                parkRole.getPermissions().forEach(parkPermission -> {
                    ParkUserPermissionDTO parkUserPermissionDTO = this.convertParkPermission(parkPermission);
                    permissionDTOList.add(parkUserPermissionDTO);
                });
                roleList.add(parkRole.getName());
            });
        }
        parkUserDTO.setRole(roleList);
        parkUserDTO.setPermissions(permissionDTOList);
        return parkUserDTO;
    }

    private ParkUser convertParkUserDTO(ParkUserDTO parkUserDTO){
        ParkUser parkUser = new ParkUser();
        BeanUtils.copyProperties(parkUserDTO,parkUser);
        return parkUser;
    }

    private ParkUserPermissionDTO convertParkPermission(ParkPermission parkPermission){
        ParkUserPermissionDTO parkUserPermissionDTO = new ParkUserPermissionDTO();
        BeanUtils.copyProperties(parkPermission,parkUserPermissionDTO);
        return parkUserPermissionDTO;
    }
}

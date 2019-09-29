package com.parkinfo.service.sysConfig.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.enums.SettingType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.sysConfig.AddUserRequest;
import com.parkinfo.request.sysConfig.QuerySysUserRequest;
import com.parkinfo.request.sysConfig.SetUserRequest;
import com.parkinfo.response.sysConfig.SysRoleResponse;
import com.parkinfo.response.sysConfig.SysUserResponse;
import com.parkinfo.service.sysConfig.ISysUserService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.oss.IOssService;
import jdk.nashorn.internal.parser.Token;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private ParkRoleRepository parkRoleRepository;

    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Autowired
    private IOssService ossService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<SysUserResponse>> searchUser(QuerySysUserRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkUser> specification = (Specification<ParkUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getRoleId())){
                SetJoin<ParkUser, ParkRole> setJoin = root.join(root.getModel().getSet("roles",ParkRole.class), JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(setJoin.get("id").as(String.class),request.getRoleId()));
            }
            if (StringUtils.isNotBlank(request.getAccount())){
                predicates.add(criteriaBuilder.like(root.get("account").as(String.class),"%"+request.getAccount()+"%"));
            }
            if (StringUtils.isNotBlank(request.getNickname())){
                predicates.add(criteriaBuilder.like(root.get("nickname").as(String.class),"%"+request.getNickname()+"%"));
            }
            if (request.getAvailable()!=null){
                predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),request.getAvailable()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ParkUser> parkUserPage = parkUserRepository.findAll(specification, pageable);
        List<SysUserResponse> list = Lists.newArrayList();
        parkUserPage.getContent().forEach(temp -> {
            SysUserResponse sysUserResponse = new SysUserResponse();
            BeanUtils.copyProperties(temp, sysUserResponse);
            List<SysRoleResponse> roleResponses = Lists.newArrayList();
            temp.getRoles().forEach(tempRole -> {
                SysRoleResponse sysRoleResponse = new SysRoleResponse();
                BeanUtils.copyProperties(tempRole, sysRoleResponse);
                roleResponses.add(sysRoleResponse);
            });
            sysUserResponse.setRoleResponses(roleResponses);
            list.add(sysUserResponse);
        });
        Page<SysUserResponse> result = new PageImpl<>(list, parkUserPage.getPageable(), parkUserPage.getTotalElements());
        return Result.<Page<SysUserResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<String> avatarUpload(HttpServletRequest request) {
        String avatarUrl = ossService.MultipartFileUpload(request, FileUploadType.AVATAR.toString());
        return Result.<String>builder().success().data(avatarUrl).build();
    }

    @Override
    public Result addUser(AddUserRequest request) {
        ParkUser newData = new ParkUser();
        BeanUtils.copyProperties(request, newData);
        newData.setDelete(false);
        newData.setAvailable(true);
        newData.setSalt(SettingType.INIT_SALT.getDefaultValue());
        String initPassword = (SettingType.INIT_PASSWORD.getDefaultValue());
        String password = new SimpleHash("MD5", initPassword, newData.getSalt(), 1024).toHex();
        newData.setPassword(password);
        Set<ParkRole> roles = tokenUtils.getLoginUser().getRoles();
        Optional<ParkRole> byAdmin = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.ADMIN.name());
        ParkRole admin = new ParkRole();
        ParkRole manager = new ParkRole();
        if(byAdmin.isPresent()){
            admin = byAdmin.get();
        }
        Optional<ParkRole> byManager = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_MANAGER.name());
        if(byManager.isPresent()){
            manager = byManager.get();
        }
        Optional<ParkRole> byId = parkRoleRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getRoleId());
        if(!byId.isPresent()){
            throw new NormalException("角色不存在");
        }
        ParkRole parkRole = byId.get();
        //超管，新增园管(id必传)，选园区，总裁总裁办默认园区
        Set<ParkInfo> parkInfos = Sets.newHashSet();
        Set<ParkRole> parkRoles = Sets.newHashSet();
        parkRoles.add(parkRole);
        if(roles.contains(admin)){
            if(parkRole.getName().equals(ParkRoleEnum.PARK_MANAGER.name())){
                if(StringUtils.isBlank(request.getParkId())){
                    throw new NormalException("园区id不能为空");
                }
                Optional<ParkInfo> byPardId = parkInfoRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getParkId());
                if(!byPardId.isPresent()){
                    ParkInfo parkInfo = byPardId.get();
                    parkInfos.add(parkInfo);
                }
            }
            else{
                Optional<ParkInfo> byParkName = parkInfoRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue("总裁园区");
                if(byParkName.isPresent()){
                    ParkInfo parkInfo = byParkName.get();
                    parkInfos.add(parkInfo);
                }
            }
        }
        //园区管理员，新增本园区员工
        else{
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            parkInfos.add(parkInfo);
        }
        newData.setParks(parkInfos);
        newData.setRoles(parkRoles);
        parkUserRepository.save(newData);
        return Result.builder().success().message("添加用户成功").build();
    }

    @Override
    public Result setUser(SetUserRequest request) {
        ParkUser parkUser = this.checkUser(request.getId());
        BeanUtils.copyProperties(request, parkUser);
        parkUserRepository.save(parkUser);
        return Result.builder().success().message("修改用户成功").build();
    }

    @Override
    public Result disableUser(String id) {
        ParkUser parkUser = this.checkUser(id);
        parkUser.setAvailable(parkUser.getAvailable() != null && !parkUser.getAvailable());
        parkUserRepository.save(parkUser);
        return Result.builder().success().message("成功").build();
    }

    @Override
    public Result deleteUser(String id) {
        ParkUser parkUser = this.checkUser(id);
        parkUser.setDelete(true);
        parkUserRepository.save(parkUser);
        return Result.builder().success().message("成功").build();
    }

    private ParkUser checkUser(String id) {
        Optional<ParkUser> optionalParkUser = parkUserRepository.findById(id);
        if (!optionalParkUser.isPresent()) {
            throw new NormalException("用户不存在");
        }
        return optionalParkUser.get();
    }
}

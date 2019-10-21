package com.parkinfo.service.sysConfig.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.DefaultEnum;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.enums.SettingType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.sysConfig.AddUserRequest;
import com.parkinfo.request.sysConfig.ChangePassRequest;
import com.parkinfo.request.sysConfig.QuerySysUserRequest;
import com.parkinfo.request.sysConfig.SetUserRequest;
import com.parkinfo.response.login.ParkInfoListResponse;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private CompanyDetailRepository companyDetailRepository;

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
            Set<ParkRole> roles = tokenUtils.getLoginUser().getRoles();
            List<String> collect = roles.stream().map(ParkRole::getName).collect(Collectors.toList());
            //去重
            criteriaQuery.distinct(true);
            if (collect.contains(ParkRoleEnum.ADMIN.toString())){
                SetJoin<ParkUser,ParkRole> roleSetJoin = root.joinSet("roles",JoinType.LEFT);
//                predicates.add(criteriaBuilder.notEqual(roleSetJoin.get("name").as(String.class),ParkRoleEnum.ADMIN.toString()));
                List<String> list = Lists.newArrayList();
                list.add(ParkRoleEnum.PARK_MANAGER.toString());
                list.add(ParkRoleEnum.AREA_MANAGER.toString());
                list.add(ParkRoleEnum.PRESIDENT.toString());
                list.add(ParkRoleEnum.GENERAL_MANAGER.toString());
                Path<Object> path = roleSetJoin.get("name");
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                list.forEach(in::value);
                predicates.add(in);
            }else {
                SetJoin<ParkUser,ParkInfo> setJoin = root.joinSet("parks",JoinType.LEFT);
//                predicates.add(criteriaBuilder.equal(setJoin.get("id").as(String.class),tokenUtils.getCurrentParkInfo().getId()));
                String id = tokenUtils.getLoginUser().getId();
                ParkUser parkUser = this.checkUser(id);
                Set<ParkInfo> parks = parkUser.getParks();
                if (parks != null && parks.size() != 0){
                    Path<Object> path = setJoin.get("id");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    parks.forEach(parkInfo -> {
                        in.value(parkInfo.getId());
                    });
                    predicates.add(in);
                }
                SetJoin<ParkUser,ParkRole> roleSetJoin = root.joinSet("roles",JoinType.LEFT);
                predicates.add(criteriaBuilder.notEqual(roleSetJoin.get("name").as(String.class),ParkRoleEnum.PARK_MANAGER.toString()));
                predicates.add(criteriaBuilder.notEqual(roleSetJoin.get("name").as(String.class),ParkRoleEnum.AREA_MANAGER.toString()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ParkUser> parkUserPage = parkUserRepository.findAll(specification, pageable);
        List<SysUserResponse> list = Lists.newArrayList();
        for(ParkUser temp : parkUserPage.getContent()) {
            SysUserResponse sysUserResponse = new SysUserResponse();
            BeanUtils.copyProperties(temp, sysUserResponse);
            if(temp.getCompanyDetail() != null){
                sysUserResponse.setCompanyId(temp.getCompanyDetail().getId());
            }
            List<SysRoleResponse> roleResponses = Lists.newArrayList();
            for(ParkRole tempRole : temp.getRoles()) {
                SysRoleResponse sysRoleResponse = new SysRoleResponse();
                BeanUtils.copyProperties(tempRole, sysRoleResponse);
                roleResponses.add(sysRoleResponse);
            }
            sysUserResponse.setRoleResponses(roleResponses);
            Set<ParkInfo> parks = temp.getParks();
            List<ParkInfoListResponse>  parkInfoListResponses = Lists.newArrayList();
            parks.forEach(parkInfo -> {
                ParkInfoListResponse parkInfoListResponse = new ParkInfoListResponse();
                parkInfoListResponse.setId(parkInfo.getId());
                parkInfoListResponse.setName(parkInfo.getName());
                parkInfoListResponses.add(parkInfoListResponse);
            });
            sysUserResponse.setParkInfoListResponses(parkInfoListResponses);
            list.add(sysUserResponse);
        }
        Page<SysUserResponse> result = new PageImpl<>(list, parkUserPage.getPageable(), parkUserPage.getTotalElements());
        return Result.<Page<SysUserResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<String> avatarUpload(HttpServletRequest request) {
        String avatarUrl = ossService.MultipartFileUpload(request, FileUploadType.AVATAR.toString());
        return Result.<String>builder().success().data(avatarUrl).build();
    }

    @Override
    @Transactional
    public Result addUser(AddUserRequest request) {
        Optional<ParkUser> byAccount = parkUserRepository.findByAccountAndAvailableIsTrueAndDeleteIsFalse(request.getAccount());
        if(byAccount.isPresent()){
            throw new NormalException("账号已存在");
        }
        ParkUser newData = new ParkUser();
        BeanUtils.copyProperties(request, newData);
        newData.setDelete(false);
        newData.setAvailable(true);
        newData.setSalt(SettingType.INIT_SALT.getDefaultValue());
        String initPassword = (SettingType.INIT_PASSWORD.getDefaultValue());
        String password = new SimpleHash("MD5", initPassword, newData.getSalt(), 1024).toHex();
        newData.setPassword(password);
        this.setUser(newData,request);
        return Result.builder().success().message("添加用户成功").build();
    }

    private void setUser(ParkUser newData,AddUserRequest request){
        newData.setRoles(null);
        newData.setParks(null);
        Set<ParkRole> roles = tokenUtils.getLoginUser().getRoles();
        Optional<ParkRole> byAdmin = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.ADMIN.name());
        ParkRole admin = new ParkRole();
        ParkRole manager = new ParkRole();
        ParkRole areaManager = new ParkRole();
        if(byAdmin.isPresent()){
            admin = byAdmin.get();
        }
        Optional<ParkRole> byManager = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_MANAGER.name());
        if(byManager.isPresent()){
            manager = byManager.get();
        }
        Optional<ParkRole> areaManagerOptional = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.AREA_MANAGER.name());
        if(areaManagerOptional.isPresent()){
            areaManager = areaManagerOptional.get();
        }
        Optional<ParkRole> byId = parkRoleRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getRoleId());
        if(!byId.isPresent()){
            throw new NormalException("角色不存在");
        }
        ParkRole parkRole = byId.get();
        //超管，新增园管(id必传)，选园区，总裁总裁办默认园区
        Set<ParkInfo> parkInfos = Sets.newHashSet();
        Set<ParkRole> parkRoles = Sets.newHashSet();
        if (parkRole.getName().equals(ParkRoleEnum.AREA_MANAGER.name())){
            parkRoles.add(manager);
            parkRoles.add(areaManager);
        }
        else {
            parkRoles.add(parkRole);
        }
        if(roles.contains(admin)){
            if(parkRole.getName().equals(ParkRoleEnum.PARK_MANAGER.name()) || parkRole.getName().equals(ParkRoleEnum.AREA_MANAGER.name())){
                if(request.getParkIds() == null || request.getParkIds().size() == 0){
                    throw new NormalException("园区不能为空");
                }
                request.getParkIds().forEach(id ->{
                    Optional<ParkInfo> byParkId = parkInfoRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
                    if(byParkId.isPresent()){
                        ParkInfo parkInfo = byParkId.get();
                        if(parkInfo.getManager() != null && StringUtils.isNotBlank(parkInfo.getManager().getId()) && !parkInfo.getManager().getId().equals(newData.getId())){
                            throw new NormalException("该园区已存在负责人");
                        }
                        parkInfos.add(parkInfo);
                        parkInfo.setManager(newData);
                        parkInfoRepository.save(parkInfo);
                    }
                });
            }
            else{
                Optional<ParkInfo> byParkName = parkInfoRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(DefaultEnum.CEO_PARK.getDefaultValue());
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
        ParkUser save = parkUserRepository.save(newData);
        if(parkRole.getName().equals(ParkRoleEnum.HR_USER.name())){
            //新增HR，需绑定企业
            Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getCompanyId());
            if(!companyDetailOptional.isPresent()){
                throw new NormalException("企业不存在");
            }
            CompanyDetail companyDetail = companyDetailOptional.get();
            if(save.getCompanyDetail() != companyDetail){
                //编辑HR，企业更新
                if(companyDetail.getParkUser() != null){
                    throw new NormalException("该企业已绑定负责人");
                }
            }
            companyDetail.setParkUser(save);
            CompanyDetail save1 = companyDetailRepository.save(companyDetail);
            save.setCompanyDetail(save1);
            parkUserRepository.save(save);
        }
    }

    @Override
    public Result changePass(ChangePassRequest request) {
        ParkUser parkUser = this.checkUser(request.getId());
//        String oldPass = new SimpleHash("MD5",request.getOldPass(),parkUser.getSalt(),1024).toHex();
//        if (!oldPass.equals(parkUser.getPassword())){
//            throw new NormalException("原密码不正确");
//        }
        String newPass = new SimpleHash("MD5",request.getNewPass(),parkUser.getSalt(),1024).toHex();
        parkUser.setPassword(newPass);
        parkUserRepository.save(parkUser);
        return Result.builder().success().message("密码修改成功").build();
    }

    @Override
    public Result setUser(SetUserRequest request) {
        ParkUser parkUser = this.checkUser(request.getId());
        BeanUtils.copyProperties(request, parkUser);
        this.setUser(parkUser,request);
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

package com.parkinfo.service.sysConfig.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkPermissionRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.request.sysConfig.QuerySysRoleRequest;
import com.parkinfo.request.sysConfig.SetPermissionRequest;
import com.parkinfo.service.sysConfig.ISysRoleService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private ParkRoleRepository parkRoleRepository;
    @Autowired
    private ParkPermissionRepository parkPermissionRepository;
    @Autowired
    private TokenUtils tokenUtils;


    @Override
    public Result<List<ParkPermission>> findPermission() {
        List<ParkPermission> permissionList = parkPermissionRepository.findAllByParentIsNullAndAvailableIsTrueAndDeleteIsFalse();
        return Result.<List<ParkPermission>>builder().success().data(permissionList).build();
    }

    @Override
    public Result<Page<ParkRole>> findRole(QuerySysRoleRequest request) {
        ParkRole exampleData = new ParkRole();
        ExampleMatcher matcher = ExampleMatcher.matching();
        exampleData.setDelete(false);
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        if (StringUtils.isNotBlank(request.getName())) {
            exampleData.setName(request.getName());
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<ParkRole> example = Example.of(exampleData, matcher);
        Page<ParkRole> parkRolePage = parkRoleRepository.findAll(example, pageable);
        return Result.<Page<ParkRole>>builder().success().data(parkRolePage).build();
    }

    @Override
    public Result setPermissions(SetPermissionRequest request) {
        Set<ParkPermission> permissionSet = new HashSet<>();
        List<String> permissionIds = request.getPermissionIds();
        ParkRole parkRole = this.checkRole(request.getRoleId());
        permissionIds.forEach(permissionId ->{
            Optional<ParkPermission> permissionOptional = parkPermissionRepository.findById(permissionId);
            if (permissionOptional.isPresent()) {
                ParkPermission parkPermission = permissionOptional.get();
                permissionSet.add(parkPermission);
            }
        });
        parkRole.setPermissions(permissionSet);
        parkRoleRepository.save(parkRole);
        return Result.builder().success().message("添加权限成功").build();
    }

    @Override
    public Result<List<ParkRole>> getAllRole() {
        Set<ParkRole> roles = tokenUtils.getLoginUser().getRoles();
        Optional<ParkRole> admin = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.ADMIN.name());
        if(admin.isPresent()){
           parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PRESIDENT.name()).get();

        }
        List<ParkRole> parkRoleList = parkRoleRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        //管理员->HR,Or,User
        //超管->总裁，总裁办，园管
        return Result.<List<ParkRole>>builder().success().data(parkRoleList).build();
    }

    private ParkRole checkRole(String roleId) {
        Optional<ParkRole> sysRoleOptional = parkRoleRepository.findById(roleId);
        if (!sysRoleOptional.isPresent()) {
            throw new NormalException("角色信息不存在");
        }
        return sysRoleOptional.get();
    }

}

package com.parkinfo.service.sysConfig.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkPermissionRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.request.sysConfig.QuerySysRoleRequest;
import com.parkinfo.request.sysConfig.SetPermissionRequest;
import com.parkinfo.response.sysConfig.ParkUserPermissionDTO;
import com.parkinfo.response.sysConfig.RolePermissionListResponse;
import com.parkinfo.response.sysConfig.SysRoleResponse;
import com.parkinfo.service.sysConfig.ISysRoleService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;


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
    @Transactional
    public Result setPermissions(SetPermissionRequest request) {
//        Set<ParkPermission> permissionSet = new HashSet<>();
        List<String> permissionIds = request.getPermissionIds();
        ParkRole parkRole = this.checkRole(request.getRoleId());
//        permissionIds.forEach(permissionId ->{
//            Optional<ParkPermission> permissionOptional = parkPermissionRepository.findById(permissionId);
//            if (permissionOptional.isPresent()) {
//                ParkPermission parkPermission = permissionOptional.get();
//                permissionSet.add(parkPermission);
//            }
//        });
        List<ParkPermission> parkPermissionList = parkPermissionRepository.findAllById(permissionIds);
        parkRole.setPermissions(new HashSet<>(parkPermissionList));
        parkRoleRepository.save(parkRole);
        return Result.builder().success().message("设置权限成功").build();
    }

    @Override
    public Result<RolePermissionListResponse> getUserPermissions(String roleId) {
        RolePermissionListResponse response = new RolePermissionListResponse();
        Optional<ParkRole> sysRoleOptional = parkRoleRepository.findById(roleId);
        if (!sysRoleOptional.isPresent()) {
            throw new NormalException("角色信息不存在");
        }
        ParkRole role = sysRoleOptional.get();
//        List<ParkPermission> permissionList = role.getPermissions().stream().filter(parkPermission -> parkPermission.getChildren()==null).collect(Collectors.toList());
        List<String> permissionIds = role.getPermissions().stream().map(ParkPermission::getId).collect(Collectors.toList());
        List<ParkPermission> childParkPermissionList = parkPermissionRepository.findByChildrenIsNullAndIdIsIn(permissionIds);
        List<ParkPermission> parentParkPermissionList = parkPermissionRepository.findByChildrenIsNotNullAndIdIsIn(permissionIds).stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(ParkPermission::getId))), ArrayList::new)
        );
        List<ParkUserPermissionDTO> childResponseList = this.convertParkPermissionDTO(childParkPermissionList);
        List<ParkUserPermissionDTO> parentResponseList = this.convertParkPermissionDTO(parentParkPermissionList);
        response.setParentPermissionList(parentResponseList);
        response.setChildPermissionList(childResponseList);
        return Result.<RolePermissionListResponse>builder().success().data(response).build();
    }

    @Override
    public Result<List<SysRoleResponse>> getAllRole() {
        Set<ParkRole> roles = tokenUtils.getLoginUser().getRoles();
        List<SysRoleResponse> parkRoleList = Lists.newArrayList();
        Optional<ParkRole> admin = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.ADMIN.name());
        if(admin.isPresent() && roles.contains(admin.get())){
            SysRoleResponse park1 = convertRoleResponse(parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PRESIDENT.name()).get());
            SysRoleResponse park2 = convertRoleResponse(parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.GENERAL_MANAGER.name()).get());
            SysRoleResponse park3 = convertRoleResponse(parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_MANAGER.name()).get());
            parkRoleList.add(park1);
            parkRoleList.add(park2);
            parkRoleList.add(park3);
        }
        //管理员->HR,Or,User
        //超管->总裁，总裁办，园管
//        Optional<ParkRole> manager = parkRoleRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_MANAGER.name());
        if(tokenUtils.getCurrentParkInfo() != null){
            ParkInfo currentParkInfo = tokenUtils.getCurrentParkInfo();
            if (parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.HR_USER.name(), currentParkInfo.getId()).isPresent()){
                SysRoleResponse park1 = convertRoleResponse(parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.HR_USER.name(), currentParkInfo.getId()).get());
                parkRoleList.add(park1);
            }
            if (parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.OFFICER.name(), currentParkInfo.getId()).isPresent()){
                SysRoleResponse park2 = convertRoleResponse(parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.OFFICER.name(), currentParkInfo.getId()).get());
                parkRoleList.add(park2);
            }
            if (parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_USER.name(), currentParkInfo.getId()).isPresent()){
                SysRoleResponse park3 = convertRoleResponse(parkRoleRepository.findByNameAndParkIdAndDeleteIsFalseAndAvailableIsTrue(ParkRoleEnum.PARK_USER.name(), currentParkInfo.getId()).get());
                parkRoleList.add(park3);
            }
        }
        return Result.<List<SysRoleResponse>>builder().success().data(parkRoleList).build();
    }

    private ParkRole checkRole(String roleId) {
        Optional<ParkRole> sysRoleOptional = parkRoleRepository.findById(roleId);
        if (!sysRoleOptional.isPresent()) {
            throw new NormalException("角色信息不存在");
        }
        return sysRoleOptional.get();
    }

    private SysRoleResponse convertRoleResponse(ParkRole parkRole){
        SysRoleResponse response = new SysRoleResponse();
        BeanUtils.copyProperties(parkRole, response);
        return response;
    }

    private List<ParkUserPermissionDTO> convertParkPermissionDTO(List<ParkPermission> childParkPermissionList) {
        List<ParkUserPermissionDTO> permissionDTOList = Lists.newArrayList();
        childParkPermissionList.forEach(permission->{
            ParkUserPermissionDTO permissionDTO = new ParkUserPermissionDTO();
            BeanUtils.copyProperties(permission,permissionDTO);
            permissionDTOList.add(permissionDTO);
        });
        return permissionDTOList;
    }

}

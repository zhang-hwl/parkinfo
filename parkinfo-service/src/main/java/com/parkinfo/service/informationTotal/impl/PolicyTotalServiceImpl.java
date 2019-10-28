package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.personalCloud.CloudDisk;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.PolicyTotalRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.response.sysConfig.SimpleParkResponse;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class PolicyTotalServiceImpl implements IPolicyTotalService {

    @Autowired
    private PolicyTotalRepository policyTotalRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<String> addPolicyTotal(PolicyTotalRequest request) {
        PolicyTotal policyTotal = new PolicyTotal();
        BeanUtils.copyProperties(request, policyTotal);
        String parkId = request.getParkInfoResponse().getId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        policyTotal.setVersion(request.getVersion());
        policyTotal.setParkInfo(parkInfo);
        policyTotal.setDelete(false);
        policyTotal.setAvailable(true);
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<String> editPolicyTotal(PolicyTotalRequest request) {
        Optional<PolicyTotal> byId = policyTotalRepository.findByIdAndDeleteIsFalse(request.getId());
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        PolicyTotal total = byId.get();
        PolicyTotal policyTotal = new PolicyTotal();
        BeanUtils.copyProperties(total, policyTotal);
        BeanUtils.copyProperties(request, policyTotal);
        policyTotal.setParkInfo(total.getParkInfo());
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<PolicyTotalRequest>> findByVersion(QueryByVersionRequest request) {
        int flag = judgePremission();   //判断查看权限
        if(flag == -1){
            return Result.<Page<PolicyTotalRequest>>builder().success().data(null).build();
        }
//        int flag = 1;
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<PolicyTotal> specification = (Specification<PolicyTotal>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVersion())){
                predicates.add(criteriaBuilder.equal(root.get("version").as(String.class), request.getVersion()));
            }
            if(flag == 0){
                predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), tokenUtils.getCurrentParkInfo().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<PolicyTotal> all = policyTotalRepository.findAll(specification, pageable);
        List<PolicyTotalRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            PolicyTotalRequest response = new PolicyTotalRequest();
            BeanUtils.copyProperties(temp, response);
            ParkInfoResponse parkInfoResponse = new ParkInfoResponse();
            parkInfoResponse.setId(temp.getParkInfo().getId());
            parkInfoResponse.setName(temp.getParkInfo().getName());
            response.setParkInfoResponse(parkInfoResponse);
            list.add(response);
        });
        Page<PolicyTotalRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<PolicyTotalRequest>>builder().success().data(result).build();
    }

    @Override
    @Transactional
    public Result<String> policyTotalImport(UploadAndVersionRequest request){
        String parkId = request.getParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        String fileName = request.getMultipartFile().getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<PolicyTotal> policyTotals = ExcelUtils.importExcel(request.getMultipartFile(), PolicyTotal.class);
            if(policyTotals != null){
                policyTotals.forEach(policyTotal -> {
                    policyTotal.setVersion(request.getVersion());
                    policyTotal.setParkInfo(parkInfo);
                    policyTotal.setDelete(false);
                    policyTotal.setAvailable(true);
                    policyTotalRepository.save(policyTotal);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> deletePolicyTotal(String id) {
        Optional<PolicyTotal> byId = policyTotalRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        PolicyTotal policyTotal = byId.get();
        policyTotal.setDelete(true);
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(String id, String parkId, HttpServletResponse response) {
        Optional<ParkUser> byId = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byId.isPresent()){
            throw new NormalException("用户不存在");
        }
        int i = judgePremissionByUserId(id);
//        int i = 1;
        List<PolicyTotal> policyTotals;
        if(i == -1){
            policyTotals = Lists.newArrayList();
        }
        else if(i == 0){
            policyTotals = policyTotalRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        }
        else{
            policyTotals = policyTotalRepository.findAllByDeleteIsFalse();
        }
        try {
            ExcelUtils.exportExcel(policyTotals, null, null, PolicyTotal.class, "zhence", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }

    @Override
    public Result<List<SimpleParkResponse>> findAll() {
        List<String> roles = tokenUtils.getLoginUserDTO().getRole();
        boolean flag = false;   //false返回本园区
        for(String temp : roles){
            if(temp.equals(ParkRoleEnum.ADMIN.name()) || temp.equals(ParkRoleEnum.PRESIDENT.name()) || temp.equals(ParkRoleEnum.GENERAL_MANAGER.name())){
                //超级管理员，总裁，总裁办获取所有园区
                flag = true;
            }
        }
        List<SimpleParkResponse> list = Lists.newArrayList();
        if(flag == false){
            ParkInfo currentParkInfo = tokenUtils.getCurrentParkInfo();
            SimpleParkResponse response = new SimpleParkResponse();
            BeanUtils.copyProperties(currentParkInfo, response);
            list.add(response);
        }
        else {
            List<ParkInfo> all = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
            for(ParkInfo parkInfo : all){
                SimpleParkResponse response = new SimpleParkResponse();
                BeanUtils.copyProperties(parkInfo, response);
                list.add(response);
            }
        }
        return Result.<List<SimpleParkResponse>>builder().success().data(list).build();
    }

    //判断权限，1为有权限，0为仅本园区
    private int judgePremission(){
        int flag = -1;
        List<String> roles = tokenUtils.getLoginUserDTO().getRole();
        for(String role : roles){
            if(role.equals(ParkRoleEnum.PRESIDENT.name()) || role.equals(ParkRoleEnum.GENERAL_MANAGER.name())){
                //总裁，总裁办
                return 1;
            }
            else if(role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.AREA_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name())){
                flag = 0;
            }
        }
        return flag;
    }

    //判断权限，1为有权限，0为仅本园区
    private int judgePremissionByUserId(String id){
        int flag = -1;
        Optional<ParkUser> byId = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byId.isPresent()){
            throw new NormalException("用户不存在");
        }
        Set<ParkRole> roleSet = byId.get().getRoles();
        List<String> roles = Lists.newArrayList();
        for(ParkRole parkRole : roleSet){
            roles.add(parkRole.getName());
        }
        for(String role : roles){
            if(role.equals(ParkRoleEnum.PRESIDENT.name()) || role.equals(ParkRoleEnum.GENERAL_MANAGER.name())){
                //总裁，总裁办
                return 1;
            }
            else if(role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name())){
                flag = 0;
            }
        }
        return flag;
    }
}

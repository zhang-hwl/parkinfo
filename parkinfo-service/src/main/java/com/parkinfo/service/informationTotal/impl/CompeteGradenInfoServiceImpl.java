package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.CompeteGradenInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.*;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.service.informationTotal.ICompeteGradenInfoService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class CompeteGradenInfoServiceImpl implements ICompeteGradenInfoService {

    @Autowired
    private CompeteGradenInfoRepository competeGradenInfoRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<String> addCompeteGradenInfo(CompeteGradenInfoRequest request) {
        String parkId = request.getParkInfoResponse().getId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if (!byIdAndDeleteIsFalse.isPresent()) {
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        CompeteGradenInfo competeGradenInfo = new CompeteGradenInfo();
        BeanUtils.copyProperties(request, competeGradenInfo);
        competeGradenInfo.setDelete(false);
        competeGradenInfo.setAvailable(true);
        competeGradenInfo.setParkInfo(parkInfo);
        competeGradenInfoRepository.save(competeGradenInfo);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editCompeteGradenInfo(CompeteGradenInfoRequest request) {
        CompeteGradenInfo competeGradenInfo = new CompeteGradenInfo();
        Optional<CompeteGradenInfo> byIdAndDeleteIsFalse = competeGradenInfoRepository.findByIdAndDeleteIsFalse(request.getId());
        if (!byIdAndDeleteIsFalse.isPresent()) {
            throw new NormalException("文件不存在");
        }
        CompeteGradenInfo info = byIdAndDeleteIsFalse.get();
        BeanUtils.copyProperties(info, competeGradenInfo);
        BeanUtils.copyProperties(request, competeGradenInfo);
        competeGradenInfo.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        competeGradenInfoRepository.save(competeGradenInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<CompeteGradenInfoRequest>> findByVersion(QueryByVersionRequest request) {
        int flag = judgePremission();   //判断查看权限
        if (flag == -1) {
            return Result.<Page<CompeteGradenInfoRequest>>builder().success().data(null).build();
        }
//        int flag = 1;
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompeteGradenInfo> specification = (Specification<CompeteGradenInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVersion())) {
                predicates.add(criteriaBuilder.equal(root.get("version").as(String.class), request.getVersion()));
            }
            if (flag == 0) {
                predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), tokenUtils.getCurrentParkInfo().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<CompeteGradenInfo> all = competeGradenInfoRepository.findAll(specification, pageable);
        List<CompeteGradenInfoRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            CompeteGradenInfoRequest response = new CompeteGradenInfoRequest();
            BeanUtils.copyProperties(temp, response);
            ParkInfoResponse parkInfoResponse = new ParkInfoResponse();
            parkInfoResponse.setId(temp.getParkInfo().getId());
            parkInfoResponse.setName(temp.getParkInfo().getName());
            response.setParkInfoResponse(parkInfoResponse);
            list.add(response);
        });
        Page<CompeteGradenInfoRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<CompeteGradenInfoRequest>>builder().success().data(result).build();
    }

    @Override
    public Result<String> competeGradenInfoImport(UploadAndVersionRequest request) {
        MultipartFile file = request.getMultipartFile();
        String parkId = request.getParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if (!byIdAndDeleteIsFalse.isPresent()) {
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<CompeteGradenInfo> competeGradenInfos = ExcelUtils.importExcel(file, CompeteGradenInfo.class);
            if (competeGradenInfos != null) {
                competeGradenInfos.forEach(competeGradenInfo -> {
                    competeGradenInfo.setVersion(request.getVersion());
                    competeGradenInfo.setAvailable(true);
                    competeGradenInfo.setDelete(false);
                    competeGradenInfo.setParkInfo(parkInfo);
                    competeGradenInfoRepository.save(competeGradenInfo);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> deleteCompeteGradenInfo(String id) {
        Optional<CompeteGradenInfo> byId = competeGradenInfoRepository.findById(id);
        if (!byId.isPresent()) {
            throw new NormalException("文件不存在");
        }
        CompeteGradenInfo competeGradenInfo = byId.get();
        competeGradenInfo.setDelete(true);
        competeGradenInfoRepository.save(competeGradenInfo);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(String id, String parkId, HttpServletResponse response) {
        Optional<ParkUser> user = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!user.isPresent()){
            throw new NormalException("用户不存在");
        }
        int i = judgePremissionById(id);
//        int i = 1;
        List<CompeteGradenInfo> competeGradenInfos;
        if(i == -1){
            competeGradenInfos = Lists.newArrayList();
        }
        else if(i == 0){
            competeGradenInfos = competeGradenInfoRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        }
        else{
            competeGradenInfos = competeGradenInfoRepository.findAllByDeleteIsFalse();
        }
        try {
            ExcelUtils.exportExcel(competeGradenInfos, null, null, CompeteGradenInfo.class, "jingzheng", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }

    //判断权限，1为有权限，0为仅本园区
    private int judgePremission() {
        int flag = -1;
        List<String> roles = tokenUtils.getLoginUserDTO().getRole();
        for (String role : roles) {
            if (role.equals(ParkRoleEnum.PRESIDENT.name()) || role.equals(ParkRoleEnum.GENERAL_MANAGER.name())) {
                //总裁，总裁办
                return 1;
            } else if (role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name()) || role.equals(ParkRoleEnum.PARK_USER.name())) {
                flag = 0;
            }
        }
            return flag;
    }

    //判断权限，1为有权限，0为仅本园区
    private int judgePremissionById(String id) {
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
        for (String role : roles) {
            if (role.equals(ParkRoleEnum.PRESIDENT.name()) || role.equals(ParkRoleEnum.GENERAL_MANAGER.name())) {
                //总裁，总裁办
                return 1;
            } else if (role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name()) || role.equals(ParkRoleEnum.PARK_USER.name())) {
                flag = 0;
            }
        }
        return flag;
    }
}
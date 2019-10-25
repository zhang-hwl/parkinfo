package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.BigEventRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkPermissionRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.service.informationTotal.IBigEventService;
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
public class BigEventServiceImpl implements IBigEventService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private BigEventRepository bigEventRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<String> add(BigEventRequest request) {
        BigEvent bigEvent = new BigEvent();
        BeanUtils.copyProperties(request, bigEvent);
        Integer year = Integer.parseInt(request.getYear());
        Integer month = Integer.parseInt(request.getMonth());
        bigEvent.setYear(year);
        bigEvent.setMonth(month);
        String parkId = request.getParkInfoResponse().getId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        bigEvent.setParkInfo(parkInfo);
        bigEvent.setDelete(false);
        bigEvent.setAvailable(true);
        bigEventRepository.save(bigEvent);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> edit(BigEventRequest request) {
        BigEvent bigEvent = new BigEvent();
        Optional<BigEvent> byIdAndDeleteIsFalse = bigEventRepository.findByIdAndDeleteIsFalse(request.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        BeanUtils.copyProperties(byIdAndDeleteIsFalse.get(), bigEvent);
        BeanUtils.copyProperties(request, bigEvent);
        bigEvent.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        Integer year = Integer.parseInt(request.getYear());
        Integer month = Integer.parseInt(request.getMonth());
        bigEvent.setYear(year);
        bigEvent.setMonth(month);
        bigEventRepository.save(bigEvent);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<BigEventRequest>> findByVersion(QueryByVersionRequest request) {
        int flag = judgePremission();   //判断查看权限
        if(flag == -1){
            return Result.<Page<BigEventRequest>>builder().success().data(null).build();
        }
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<BigEvent> specification = (Specification<BigEvent>) (root, criteriaQuery, criteriaBuilder) -> {
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
        Page<BigEvent> all = bigEventRepository.findAll(specification, pageable);
        List<BigEventRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            BigEventRequest response = new BigEventRequest();
            BeanUtils.copyProperties(temp, response);
            response.setYear(String.valueOf(temp.getYear()));
            response.setMonth(String.valueOf(temp.getMonth()));
            ParkInfoResponse parkInfoResponse = new ParkInfoResponse();
            parkInfoResponse.setId(temp.getParkInfo().getId());
            parkInfoResponse.setName(temp.getParkInfo().getName());
            response.setParkInfoResponse(parkInfoResponse);
            list.add(response);
        });
        Page<BigEventRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<BigEventRequest>>builder().success().data(result).build();
    }

    @Override
    public Result<String> myImport(UploadAndVersionRequest request) {
        MultipartFile file = request.getMultipartFile();
        String parkId = request.getParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<BigEvent> bigEvents = ExcelUtils.importExcel(file, BigEvent.class);
            if(bigEvents != null){
                bigEvents.forEach(bigEvent -> {
                    bigEvent.setVersion(request.getVersion());
                    bigEvent.setParkInfo(parkInfo);
                    bigEvent.setDelete(false);
                    bigEvent.setAvailable(true);
                    bigEventRepository.save(bigEvent);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> delete(String id) {
        Optional<BigEvent> byId = bigEventRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        BigEvent bigEvent = byId.get();
        bigEvent.setDelete(true);
        bigEventRepository.save(bigEvent);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(String id, String parkId, HttpServletResponse response) {
        //仅下载权限内的文件
        int i = judgePremissionById(id);
        Optional<ParkUser> byIdAndDeleteIsFalse = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("用户不存在");
        }
        List<BigEvent> list;
        if(i == -1){
            list = Lists.newArrayList();
        }
        else if(i == 0){
            list = bigEventRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        }
        else{
            list = bigEventRepository.findAllByDeleteIsFalse();
        }
        try {
            ExcelUtils.exportExcel(list, null, null, BigEvent.class, "shiji", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }

    //判断权限，1为查看所有，0为仅本园区，-1不能查看
    private int judgePremission(){
        int flag = -1;
        List<String> roles = tokenUtils.getLoginUserDTO().getRole();
        for(String role : roles){
            if(role.equals(ParkRoleEnum.PRESIDENT.name()) || role.equals(ParkRoleEnum.GENERAL_MANAGER.name())){
                //总裁，总裁办
                return 1;
            }
            else if(role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.AREA_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name()) || role.equals(ParkRoleEnum.HR_USER.name())){
                flag = 0;
            }
        }
        return flag;
    }

    //判断权限，1为查看所有，0为仅本园区，-1不能查看
    private int judgePremissionById(String id){
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
            else if(role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.AREA_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name()) || role.equals(ParkRoleEnum.HR_USER.name())){
                flag = 0;
            }
        }
        return flag;
    }

}

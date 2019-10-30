package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.CheckRecordRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.*;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.service.informationTotal.ICheckRecordService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CheckRecordServiceImpl implements ICheckRecordService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private CheckRecordRepository checkRecordRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<String> addCheckRecord(CheckRecordRequest request) {
        CheckRecord checkRecord = new CheckRecord();
        BeanUtils.copyProperties(request, checkRecord);
        String parkId = request.getParkInfoResponse().getId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        checkRecord.setParkInfo(parkInfo);
        checkRecord.setDelete(false);
        checkRecord.setAvailable(true);
        checkRecordRepository.save(checkRecord);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editCheckRecord(CheckRecordRequest request) {
        CheckRecord checkRecord = new CheckRecord();
        Optional<CheckRecord> byIdAndDeleteIsFalse = checkRecordRepository.findByIdAndDeleteIsFalse(request.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        BeanUtils.copyProperties(byIdAndDeleteIsFalse.get(), checkRecord);
        BeanUtils.copyProperties(request, checkRecord);
        checkRecord.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        checkRecordRepository.save(checkRecord);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<CheckRecordRequest>> findByVersion(QueryByVersionRequest request) {
        int flag = judgePremission();   //判断查看权限
        if(flag == -1){
            List<CheckRecordRequest> list = Lists.newArrayList();
            return Result.<Page<CheckRecordRequest>>builder().success().data(new PageImpl<>(list)).build();
        }
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CheckRecord> specification = (Specification<CheckRecord>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVersion())){
                predicates.add(criteriaBuilder.equal(root.get("version").as(String.class), request.getVersion()));
            }
            if(flag == 0){
                predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), tokenUtils.getCurrentParkInfo().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CheckRecord> all = checkRecordRepository.findAll(specification, pageable);
        List<CheckRecordRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            CheckRecordRequest response = new CheckRecordRequest();
            BeanUtils.copyProperties(temp, response);
            ParkInfoResponse parkInfoResponse = new ParkInfoResponse();
            parkInfoResponse.setName(temp.getParkInfo().getName());
            parkInfoResponse.setId(temp.getParkInfo().getId());
            response.setParkInfoResponse(parkInfoResponse);
            list.add(response);
        });
        Page<CheckRecordRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<CheckRecordRequest>>builder().success().data(result).build();
    }

    @Override
    public Result<String> checkRecordImport(UploadAndVersionRequest request) {
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
            List<CheckRecord> bigEvents = ExcelUtils.importExcel(file, CheckRecord.class);
            if(bigEvents != null){
                bigEvents.forEach(checkRecord -> {
                    checkRecord.setVersion(request.getVersion());
                    checkRecord.setParkInfo(parkInfo);
                    checkRecord.setDelete(false);
                    checkRecord.setAvailable(true);
                    checkRecordRepository.save(checkRecord);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> deleteCheckRecord(String id) {
        Optional<CheckRecord> byId = checkRecordRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        CheckRecord checkRecord = byId.get();
        checkRecord.setDelete(true);
        checkRecordRepository.save(checkRecord);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(String id, String parkId, HttpServletResponse response) {
        int i = judgePremissionById(id);
        List<CheckRecord> list;
        Optional<ParkUser> byId = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byId.isPresent()){
            throw new NormalException("用户不存在");
        }
        if(i == -1){
            list = Lists.newArrayList();
        }
         else if(i == 0){
            list = checkRecordRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        }
        else{
            list = checkRecordRepository.findAllByDeleteIsFalse();
        }
        try {
            ExcelUtils.exportExcel(list, null, null, CheckRecord.class, "dianjian", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }

    //判断权限，1为有权限，0为仅本园区
    private int judgePremission(){
        int flag = -1;
        List<String> roles = tokenUtils.getLoginUserDTO().getRole();
        for(String role : roles){
            if(role.equals(ParkRoleEnum.HR_USER.name())){
                flag = -1;
            }
            else{
                return 1;
            }
        }
        return flag;
    }

    //判断权限，1为有权限，0为仅本园区
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
            if(role.equals(ParkRoleEnum.HR_USER.name())){
                flag = -1;
            }
            else{
                return 1;
            }
        }
        return flag;
    }
}

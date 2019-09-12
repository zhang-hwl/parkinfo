package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.CheckRecordRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.service.informationTotal.ICheckRecordService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class CheckRecordServiceImpl implements ICheckRecordService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private CheckRecordRepository checkRecordRepository;

    @Override
    public Result<String> addCheckRecord(CheckRecordRequest request) {
        CheckRecord checkRecord = new CheckRecord();
        BeanUtils.copyProperties(request, checkRecord);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
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
        BeanUtils.copyProperties(request, checkRecord);
        Optional<CheckRecord> byIdAndDeleteIsFalse = checkRecordRepository.findByIdAndDeleteIsFalse(checkRecord.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        checkRecord.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        checkRecordRepository.save(byIdAndDeleteIsFalse.get());
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<CheckRecordRequest>> findByVersion(String version) {
        judgePremission();
        List<CheckRecord> byVersionAndDeleteIsFalse = checkRecordRepository.findByVersionAndDeleteIsFalse(version);
        List<CheckRecordRequest> list = Lists.newArrayList();
        byVersionAndDeleteIsFalse.forEach(temp -> {
            CheckRecordRequest request = new CheckRecordRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<CheckRecordRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> checkRecordImport(MultipartFile file) {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
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
    public Result<String> checkRecordExport(HttpServletResponse response) {
        CheckRecord checkRecord = new CheckRecord();
        List<CheckRecord> list = new ArrayList<>();
        list.add(checkRecord);
        try {
            ExcelUtils.exportExcel(list, "点检记录表", "点检记录表", CheckRecord.class, "dianjian", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<CheckRecordRequest>> findAll() {
        judgePremission();
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        List<CheckRecord> allByDeleteIsFalse = checkRecordRepository.findAllByDeleteIsFalse();
        List<CheckRecordRequest> list = Lists.newArrayList();
        allByDeleteIsFalse.forEach(temp -> {
            CheckRecordRequest request = new CheckRecordRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<CheckRecordRequest>>builder().success().data(list).build();
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
    public void download(HttpServletResponse response, String version) {
        judgePremission();
        List<CheckRecord> list = Lists.newArrayList();
        if(StringUtils.isBlank(version)){
            list.addAll(checkRecordRepository.findByVersionAndDeleteIsFalse(version));
        }
        else{
            list.addAll(checkRecordRepository.findAllByDeleteIsFalse());
        }
        try {
            ExcelUtils.exportExcel(list, "点检记录表", "点检记录表", CheckRecord.class, "dianjian", response);
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
        if(flag == -1){
            throw new ShiroException();
        }
        return flag;
    }
}

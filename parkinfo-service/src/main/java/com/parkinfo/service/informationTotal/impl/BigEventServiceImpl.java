package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.BigEventRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkPermissionRepository;
import com.parkinfo.repository.userConfig.ParkRoleRepository;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.service.informationTotal.IBigEventService;
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
public class BigEventServiceImpl implements IBigEventService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private BigEventRepository bigEventRepository;

    @Override
    public Result<String> add(BigEventRequest request) {
        BigEvent bigEvent = new BigEvent();
        BeanUtils.copyProperties(request, bigEvent);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
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
        BeanUtils.copyProperties(request, bigEvent);
        Optional<BigEvent> byIdAndDeleteIsFalse = bigEventRepository.findByIdAndDeleteIsFalse(bigEvent.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        bigEvent.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        bigEventRepository.save(byIdAndDeleteIsFalse.get());
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<BigEventRequest>> findByVersion(String version) {
        int flag = judgePremission();   //判断查看权限
        ParkUserDTO parkUserDTO = tokenUtils.getLoginUserDTO();
        if(parkUserDTO == null){
            throw new NormalException("token不存在或已过期");
        }
        String parkId = parkUserDTO.getCurrentParkId();
        List<BigEvent> byVersionAndDeleteIsFalse = bigEventRepository.findByVersionAndDeleteIsFalse(version);
        List<BigEventRequest> list = Lists.newArrayList();
        if(flag == 0){
            //本园区可见
            byVersionAndDeleteIsFalse.forEach(temp -> {
                if(temp.getParkInfo().getId().equals(parkId)){
                    BigEventRequest request = new BigEventRequest();
                    BeanUtils.copyProperties(temp, request);
                    list.add(request);
                }
            });
        }
        else{
            byVersionAndDeleteIsFalse.forEach(temp -> {
                BigEventRequest request = new BigEventRequest();
                BeanUtils.copyProperties(temp, request);
                list.add(request);
            });
        }
        return Result.<List<BigEventRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> myImport(MultipartFile file) {
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
            List<BigEvent> bigEvents = ExcelUtils.importExcel(file, BigEvent.class);
            if(bigEvents != null){
                bigEvents.forEach(bigEvent -> {
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
    public Result<String> export(HttpServletResponse response) {
        BigEvent bigEvent = new BigEvent();
        List<BigEvent> list = new ArrayList<>();
        list.add(bigEvent);
        try {
            ExcelUtils.exportExcel(list, "园区大事记", "园区大事记统计模板", BigEvent.class, "shiji", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<BigEventRequest>> findAll() {
        int flag = judgePremission();   //判断查看权限
        ParkUserDTO parkUserDTO = tokenUtils.getLoginUserDTO();
        if(parkUserDTO == null){
            throw new NormalException("token不存在或已过期");
        }
        String parkId = parkUserDTO.getCurrentParkId();
        List<BigEvent> allByDeleteIsFalse = bigEventRepository.findAllByDeleteIsFalse();
        List<BigEventRequest> list = Lists.newArrayList();
        if(flag == 0){
            allByDeleteIsFalse.forEach(temp -> {
                if(temp.getParkInfo().getId().equals(parkId)){
                    BigEventRequest request = new BigEventRequest();
                    BeanUtils.copyProperties(temp, request);
                    list.add(request);
                }
            });
        }
        else{
            allByDeleteIsFalse.forEach(temp -> {
                BigEventRequest request = new BigEventRequest();
                BeanUtils.copyProperties(temp, request);
                list.add(request);
            });
        }
        return Result.<List<BigEventRequest>>builder().success().data(list).build();
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
    public void download(HttpServletResponse response, String version) {
        //仅下载权限内的文件
        List<BigEventRequest> bigEvents;
        if(StringUtils.isNotBlank(version)){
            bigEvents = findByVersion(version).getData();
        }
        else{
            bigEvents = findAll().getData();
        }
        List<BigEvent> list = Lists.newArrayList();
        bigEvents.forEach(temp -> {
            BigEvent bigEvent = new BigEvent();
            BeanUtils.copyProperties(temp, bigEvent);
            list.add(bigEvent);
        });
        try {
            ExcelUtils.exportExcel(list, "园区大事记", "园区大事记", BigEvent.class, "shiji", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
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
            else if(role.equals(ParkRoleEnum.PARK_MANAGER.name()) || role.equals(ParkRoleEnum.OFFICER.name()) || role.equals(ParkRoleEnum.HR_USER.name())){
                flag = 0;
            }
        }
        if(flag == -1){
            throw new ShiroException();
        }
        return flag;
    }

}

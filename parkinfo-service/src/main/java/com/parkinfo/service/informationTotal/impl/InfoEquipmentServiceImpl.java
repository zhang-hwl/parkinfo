package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.InfoEquipment;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.InfoEquipmentRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.request.infoTotalRequest.InfoEquipmentRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.service.informationTotal.IInfoEquipmentService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InfoEquipmentServiceImpl implements IInfoEquipmentService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private InfoEquipmentRepository infoEquipmentRepository;

    @Override
    public Result<String> add(InfoEquipmentRequest request) {
        InfoEquipment infoEquipment = new InfoEquipment();
        BeanUtils.copyProperties(request, infoEquipment);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        infoEquipment.setParkInfo(parkInfo);
        infoEquipment.setDelete(false);
        infoEquipment.setAvailable(true);
        infoEquipmentRepository.save(infoEquipment);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<String> edit(InfoEquipmentRequest request) {
        Optional<InfoEquipment> byId = infoEquipmentRepository.findByIdAndDeleteIsFalse(request.getId());
        if(byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        InfoEquipment infoEquipment = new InfoEquipment();
        BeanUtils.copyProperties(request, infoEquipment);
        infoEquipment.setParkInfo(byId.get().getParkInfo());
        infoEquipmentRepository.save(infoEquipment);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<InfoEquipmentRequest>> findByVersion(String version) {
        if(StringUtils.isBlank(version)){
            return findAll();
        }
        List<InfoEquipment> byVersion = infoEquipmentRepository.findByVersionAndDeleteIsFalse(version);
        List<InfoEquipmentRequest> list = Lists.newArrayList();
        byVersion.forEach(temp -> {
            InfoEquipmentRequest request = new InfoEquipmentRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<InfoEquipmentRequest>>builder().success().data(list).build();
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
            List<InfoEquipment> infoEquipments = ExcelUtils.importExcel(file, InfoEquipment.class);
            if(infoEquipments != null){
                infoEquipments.forEach(infoEquipment -> {
                    infoEquipment.setParkInfo(parkInfo);
                    infoEquipment.setDelete(false);
                    infoEquipment.setAvailable(true);
                    infoEquipmentRepository.save(infoEquipment);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> export(HttpServletResponse response) {
        InfoEquipment infoEquipment = new InfoEquipment();
        List<InfoEquipment> list = new ArrayList<>();
        list.add(infoEquipment);
        try {
            ExcelUtils.exportExcel(list, "信息化设备", "信息化设备", InfoEquipment.class, "xinxi", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<InfoEquipmentRequest>> findAll() {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        List<InfoEquipment> allByDeleteIsFalse = infoEquipmentRepository.findAllByDeleteIsFalse();
        List<InfoEquipmentRequest> list = Lists.newArrayList();
        allByDeleteIsFalse.forEach(temp -> {
            InfoEquipmentRequest request = new InfoEquipmentRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<InfoEquipmentRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> delete(String id) {
        Optional<InfoEquipment> byId = infoEquipmentRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        InfoEquipment infoEquipment = new InfoEquipment();
        infoEquipment.setDelete(true);
        infoEquipmentRepository.save(infoEquipment);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(HttpServletResponse response, String version) {
        List<InfoEquipment> list = Lists.newArrayList();
        if(StringUtils.isNotBlank(version)){
            list.addAll(infoEquipmentRepository.findByVersionAndDeleteIsFalse(version));
        }
        else{
            list.addAll(infoEquipmentRepository.findAllByDeleteIsFalse());
        }
        try {
            ExcelUtils.exportExcel(list, "信息化设备", "信息化设备", InfoEquipment.class, "xinxi", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }
}

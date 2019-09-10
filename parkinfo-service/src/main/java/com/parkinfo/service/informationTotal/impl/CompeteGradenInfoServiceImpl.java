package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.CompeteGradenInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import com.parkinfo.service.informationTotal.ICompeteGradenInfoService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
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
public class CompeteGradenInfoServiceImpl implements ICompeteGradenInfoService {

    @Autowired
    private CompeteGradenInfoRepository competeGradenInfoRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Override
    public Result<String> addCompeteGradenInfo(CompeteGradenInfoRequest request) {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
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
        BeanUtils.copyProperties(request, competeGradenInfo);
        Optional<CompeteGradenInfo> byIdAndDeleteIsFalse = competeGradenInfoRepository.findByIdAndDeleteIsFalse(competeGradenInfo.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        competeGradenInfo.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        competeGradenInfoRepository.save(byIdAndDeleteIsFalse.get());
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<CompeteGradenInfoRequest>> findByVersion(String version) {
        List<CompeteGradenInfo> byVersionAndDeleteIsFalse = competeGradenInfoRepository.findByVersionAndDeleteIsFalse(version);
        List<CompeteGradenInfoRequest> list = Lists.newArrayList();
        byVersionAndDeleteIsFalse.forEach(temp -> {
            CompeteGradenInfoRequest request = new CompeteGradenInfoRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<CompeteGradenInfoRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> competeGradenInfoImport(MultipartFile file) {
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
            List<CompeteGradenInfo> competeGradenInfos = ExcelUtils.importExcel(file, CompeteGradenInfo.class);
            if(competeGradenInfos != null){
                competeGradenInfos.forEach(competeGradenInfo -> {
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
    public Result<String> competeGradenInfoExport(HttpServletResponse response) {
        CompeteGradenInfo competeGradenInfo = new CompeteGradenInfo();
        List<CompeteGradenInfo> list = new ArrayList<>();
        list.add(competeGradenInfo);
        try {
            ExcelUtils.exportExcel(list, "园区竞争信息模板", "园区竞争信息模板", CompeteGradenInfo.class, "jingzheng", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<CompeteGradenInfoRequest>> findAll() {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        List<CompeteGradenInfo> allByDeleteIsFalse = competeGradenInfoRepository.findAllByDeleteIsFalse();
        List<CompeteGradenInfoRequest> list = Lists.newArrayList();
        allByDeleteIsFalse.forEach(temp -> {
            CompeteGradenInfoRequest request = new CompeteGradenInfoRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<CompeteGradenInfoRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> deleteCompeteGradenInfo(String id) {
        Optional<CompeteGradenInfo> byId = competeGradenInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        CompeteGradenInfo competeGradenInfo = byId.get();
        competeGradenInfo.setDelete(true);
        competeGradenInfoRepository.save(competeGradenInfo);
        return Result.<String>builder().success().data("删除成功").build();
    }
}

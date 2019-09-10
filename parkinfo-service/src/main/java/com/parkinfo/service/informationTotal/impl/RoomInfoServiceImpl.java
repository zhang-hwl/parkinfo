package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.RoomInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.service.informationTotal.IRoomInfoService;
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
public class RoomInfoServiceImpl implements IRoomInfoService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Override
    public Result<String> add(RoomInfoRequest request) {
        RoomInfo roomInfo = new RoomInfo();
        BeanUtils.copyProperties(request, roomInfo);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        roomInfo.setParkInfo(parkInfo);
        roomInfo.setDelete(false);
        roomInfo.setAvailable(true);
        roomInfoRepository.save(roomInfo);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> edit(RoomInfoRequest request) {
        RoomInfo roomInfo = new RoomInfo();
        BeanUtils.copyProperties(request, roomInfo);
        Optional<RoomInfo> byIdAndDeleteIsFalse = roomInfoRepository.findByIdAndDeleteIsFalse(roomInfo.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        roomInfo.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        roomInfoRepository.save(roomInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<RoomInfoRequest>> findByVersion(String version) {
        List<RoomInfo> byVersionAndDeleteIsFalse = roomInfoRepository.findByVersionAndDeleteIsFalse(version);
        List<RoomInfoRequest> list = Lists.newArrayList();
        byVersionAndDeleteIsFalse.forEach(temp -> {
            RoomInfoRequest request = new RoomInfoRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<RoomInfoRequest>>builder().success().data(list).build();
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
            List<RoomInfo> roomInfos = ExcelUtils.importExcel(file, RoomInfo.class);
            if(parkInfo != null){
                roomInfos.forEach(roomInfo -> {
                    roomInfo.setParkInfo(parkInfo);
                    roomInfo.setDelete(false);
                    roomInfo.setAvailable(true);
                    roomInfoRepository.save(roomInfo);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> export(HttpServletResponse response) {
        RoomInfo roomInfo = new RoomInfo();
        List<RoomInfo> list = new ArrayList<>();
        list.add(roomInfo);
        try {
            ExcelUtils.exportExcel(list, "本园区房间统计", "本园区房间统计模板", RoomInfo.class, "fangjian", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<RoomInfoRequest>> findAll() {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        List<RoomInfo> allByDeleteIsFalse = roomInfoRepository.findAllByDeleteIsFalse();
        List<RoomInfoRequest> list = Lists.newArrayList();
        allByDeleteIsFalse.forEach(temp -> {
            RoomInfoRequest request = new RoomInfoRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<RoomInfoRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> delete(String id) {
        Optional<RoomInfo> byId = roomInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        RoomInfo roomInfo = byId.get();
        roomInfo.setDelete(true);
        roomInfoRepository.save(roomInfo);
        return Result.<String>builder().success().data("删除成功").build();
    }
}

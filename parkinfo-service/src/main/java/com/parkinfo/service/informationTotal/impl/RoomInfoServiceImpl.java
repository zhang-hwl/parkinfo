package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.RoomInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.IRoomInfoService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
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
    @Autowired
    private ParkUserRepository parkUserRepository;

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
        Optional<RoomInfo> byIdAndDeleteIsFalse = roomInfoRepository.findByIdAndDeleteIsFalse(request.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        BeanUtils.copyProperties(byIdAndDeleteIsFalse.get(), roomInfo);
        BeanUtils.copyProperties(request, roomInfo);
        roomInfo.setParkInfo(byIdAndDeleteIsFalse.get().getParkInfo());
        roomInfoRepository.save(roomInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<RoomInfoRequest>> findByVersion(QueryByVersionRequest request) {
        ParkUserDTO loginUserDTO = tokenUtils.getLoginUserDTO();
        if(loginUserDTO == null){
            throw new NormalException("token不存在或已过期");
        }
        String parkId = loginUserDTO.getCurrentParkId();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<RoomInfo> specification = (Specification<RoomInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVersion())){
                predicates.add(criteriaBuilder.equal(root.get("version").as(String.class), request.getVersion()));
            }
            predicates.add(criteriaBuilder.equal(root.get("parkInfo").get("id").as(String.class), parkId));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<RoomInfo> all = roomInfoRepository.findAll(specification, pageable);
        List<RoomInfoRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            RoomInfoRequest response = new RoomInfoRequest();
            BeanUtils.copyProperties(temp, response);
            list.add(response);
        });
        Page<RoomInfoRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<RoomInfoRequest>>builder().success().data(result).build();
    }

    @Override
    public Result<String> myImport(UploadAndVersionRequest request) {
        MultipartFile file = request.getMultipartFile();
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
            if(roomInfos != null){
                roomInfos.forEach(roomInfo -> {
                    roomInfo.setVersion(request.getVersion());
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

    @Override
    public void download(String id, HttpServletResponse response) {
        Optional<ParkUser> byId = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byId.isPresent()){
            throw new NormalException("用户不存在");
        }
        String parkId = byId.get().getId();
        List<RoomInfo> list = roomInfoRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        try {
            ExcelUtils.exportExcel(list, null, null, RoomInfo.class, "fangjian", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }
}

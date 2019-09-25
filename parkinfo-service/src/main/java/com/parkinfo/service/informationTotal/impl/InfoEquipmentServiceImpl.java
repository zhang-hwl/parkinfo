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
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.IInfoEquipmentService;
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
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        InfoEquipment infoEquipment = new InfoEquipment();
        BeanUtils.copyProperties(byId.get(), infoEquipment);
        BeanUtils.copyProperties(request, infoEquipment);
        infoEquipment.setParkInfo(byId.get().getParkInfo());
        infoEquipmentRepository.save(infoEquipment);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<Page<InfoEquipmentRequest>> findByVersion(QueryByVersionRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<InfoEquipment> specification = (Specification<InfoEquipment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getVersion())){
                predicates.add(criteriaBuilder.equal(root.get("version").as(String.class), request.getVersion()));
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<InfoEquipment> all = infoEquipmentRepository.findAll(specification, pageable);
        List<InfoEquipmentRequest> list = Lists.newArrayList();
        all.forEach(temp -> {
            InfoEquipmentRequest response = new InfoEquipmentRequest();
            BeanUtils.copyProperties(temp, response);
            list.add(response);
        });
        Page<InfoEquipmentRequest> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<InfoEquipmentRequest>>builder().success().data(result).build();
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
            List<InfoEquipment> infoEquipments = ExcelUtils.importExcel(file, InfoEquipment.class);
            if(infoEquipments != null){
                infoEquipments.forEach(infoEquipment -> {
                    infoEquipment.setVersion(request.getVersion());
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
    public Result<String> delete(String id) {
        Optional<InfoEquipment> byId = infoEquipmentRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        InfoEquipment infoEquipment = byId.get();
        infoEquipment.setDelete(true);
        infoEquipmentRepository.save(infoEquipment);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(String id, HttpServletResponse response) {
        List<InfoEquipment> list = infoEquipmentRepository.findByDeleteIsFalse();
//        int size = list.size();
//        for(int i = 0; i < size; i++){
//            InfoEquipment infoEquipment = list.get(i);
//            infoEquipment.setParkInfo(null);
//            list.set(i, infoEquipment);
//        }
        try {
            ExcelUtils.exportExcel(list, null, null, InfoEquipment.class, "xinxi", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }
}

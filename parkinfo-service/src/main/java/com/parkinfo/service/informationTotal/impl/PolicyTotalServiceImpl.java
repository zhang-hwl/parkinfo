package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.PolicyTotalRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PolicyTotalServiceImpl implements IPolicyTotalService {

    @Autowired
    private PolicyTotalRepository policyTotalRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Override
    public Result<String> addPolicyTotal(PolicyTotalRequest request) {
        PolicyTotal policyTotal = new PolicyTotal();
        BeanUtils.copyProperties(request, policyTotal);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        policyTotal.setParkInfo(parkInfo);
        policyTotal.setDelete(false);
        policyTotal.setAvailable(true);
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<String> editPolicyTotal(PolicyTotalRequest request) {
        Optional<PolicyTotal> byId = policyTotalRepository.findByIdAndDeleteIsFalse(request.getId());
        if(byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        PolicyTotal policyTotal = new PolicyTotal();
        BeanUtils.copyProperties(request, policyTotal);
        policyTotal.setParkInfo(byId.get().getParkInfo());
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<PolicyTotalRequest>> findByVersion(String version) {
        List<PolicyTotal> byVersion = policyTotalRepository.findByVersionAndDeleteIsFalse(version);
        List<PolicyTotalRequest> list = Lists.newArrayList();
        byVersion.forEach(temp -> {
            PolicyTotalRequest request = new PolicyTotalRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<PolicyTotalRequest>>builder().success().data(list).build();
    }

    @Override
    @Transactional
    public Result<String> policyTotalImport(MultipartFile file){
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
            List<PolicyTotal> policyTotals = ExcelUtils.importExcel(file, PolicyTotal.class);
            if(policyTotals != null){
                policyTotals.forEach(policyTotal -> {
                    policyTotal.setParkInfo(parkInfo);
                    policyTotal.setDelete(false);
                    policyTotal.setAvailable(true);
                    policyTotalRepository.save(policyTotal);
                });
            }
        } catch (Exception e) {
            throw new NormalException("上传失败");
        }
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    @Transactional
    public Result<String> policyTotalExport(HttpServletResponse response){
        PolicyTotal policyTotal = new PolicyTotal();
        List<PolicyTotal> list = new ArrayList<>();
        list.add(policyTotal);
        try {
            ExcelUtils.exportExcel(list, "政策统计模板", "政策统计模板", PolicyTotal.class, "zhence", response);
        } catch (Exception e) {
            throw new NormalException("模板下载失败");
        }
        return Result.<String>builder().success().data("模板下载成功").build();
    }

    @Override
    public Result<List<PolicyTotalRequest>> findAll() {
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("该园区不存在");
        }
        List<PolicyTotal> allByDeleteIsFalse = policyTotalRepository.findAllByDeleteIsFalse();
        List<PolicyTotalRequest> list = Lists.newArrayList();
        allByDeleteIsFalse.forEach(temp -> {
            PolicyTotalRequest request = new PolicyTotalRequest();
            BeanUtils.copyProperties(temp, request);
            list.add(request);
        });
        return Result.<List<PolicyTotalRequest>>builder().success().data(list).build();
    }

    @Override
    public Result<String> deletePolicyTotal(String id) {
        Optional<PolicyTotal> byId = policyTotalRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        PolicyTotal policyTotal = byId.get();
        policyTotal.setDelete(true);
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public void download(HttpServletResponse response, String version) {
        List<PolicyTotal> list = Lists.newArrayList();
        if(StringUtils.isBlank(version) || version.equals("''") || version.equals("null")){
            list.addAll(policyTotalRepository.findByVersionAndDeleteIsFalse(version));
        }
        else{
            list.addAll(policyTotalRepository.findAllByDeleteIsFalse());
        }
        try {
            ExcelUtils.exportExcel(list, "政策统计", "政策统计", PolicyTotal.class, "zhence", response);
        } catch (Exception e) {
            throw new NormalException("下载失败");
        }
    }

    //判断是否有权限
    public boolean judgeLimit(){
        boolean flag = false;
        
        return flag;
    }
}

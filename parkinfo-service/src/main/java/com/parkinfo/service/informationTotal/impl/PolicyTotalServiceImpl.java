package com.parkinfo.service.informationTotal.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.PolicyTotalRepository;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import com.parkinfo.util.ExcelUtils;
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

    @Override
    public Result<String> addPolicyTotal(PolicyTotal policyTotal) {
        //todo 设置园区ID
        String gradenId = "";
        policyTotal.setDelete(false);
        policyTotal.setAvailable(true);
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<String> editPolicyTotal(PolicyTotal policyTotal) {
        Optional<PolicyTotal> byId = policyTotalRepository.findById(policyTotal.getId());
        if(byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyTotalRepository.save(policyTotal);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<PolicyTotal>> findByVersion(String version) {
        List<PolicyTotal> byVersion = policyTotalRepository.findByVersionAndDeleteIsFalse(version);
        return Result.<List<PolicyTotal>>builder().success().data(byVersion).build();
    }

    @Override
    @Transactional
    public Result<String> policyTotalImport(MultipartFile file){
        //todo 设置园区
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<PolicyTotal> policyTotals = ExcelUtils.importExcel(file, PolicyTotal.class);
            if(policyTotals != null){
                policyTotals.forEach(policyTotal -> {
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
    public Result<List<PolicyTotal>> findAll() {
        //todo 根据园区
        List<PolicyTotal> allByDeleteIsFalse = policyTotalRepository.findAllByDeleteIsFalse();
        return Result.<List<PolicyTotal>>builder().success().data(allByDeleteIsFalse).build();
    }

    @Override
    public Result<String> deletePolicyTotal(String id) {
        Optional<PolicyTotal> byId = policyTotalRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyTotalRepository.delete(byId.get());
        return Result.<String>builder().success().data("删除成功").build();
    }
}

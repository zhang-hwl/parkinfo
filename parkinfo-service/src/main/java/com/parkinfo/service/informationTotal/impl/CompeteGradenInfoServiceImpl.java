package com.parkinfo.service.informationTotal.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.CompeteGradenInfoRepository;
import com.parkinfo.service.informationTotal.ICompeteGradenInfoService;
import com.parkinfo.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompeteGradenInfoServiceImpl implements ICompeteGradenInfoService {

    @Autowired
    private CompeteGradenInfoRepository competeGradenInfoRepository;

    @Override
    public Result<String> addCompeteGradenInfo(CompeteGradenInfo competeGradenInfo) {
        String gradenId = "";
        competeGradenInfo.setDelete(false);
        competeGradenInfo.setAvailable(true);
        competeGradenInfoRepository.save(competeGradenInfo);
        return null;
    }

    @Override
    public Result<String> editCompeteGradenInfo(CompeteGradenInfo competeGradenInfo) {
        return null;
    }

    @Override
    public Result<List<PolicyTotal>> findByVersion(String version) {
        return null;
    }

    @Override
    public Result<String> competeGradenInfoImport(MultipartFile file) {
        //todo 设置园区
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
    public Result<List<PolicyTotal>> findAll() {
        return null;
    }

    @Override
    public Result<String> deleteCompeteGradenInfo(String id) {
        return null;
    }
}

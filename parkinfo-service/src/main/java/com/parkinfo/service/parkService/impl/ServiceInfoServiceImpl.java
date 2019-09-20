package com.parkinfo.service.parkService.impl;

import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.enums.EnclosureType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnclosureTotalRepository;
import com.parkinfo.response.parkService.CompanyDataResponse;
import com.parkinfo.service.parkService.IServiceInfoService;
import com.parkinfo.token.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceInfoServiceImpl implements IServiceInfoService {
    @Autowired
    private CompanyDetailRepository companyDetailRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private EnclosureTotalRepository enclosureTotalRepository;

    @Override
    public Result<CompanyDataResponse> getCompanyDataResponse() {
        ParkUserDTO loginUserDTO = tokenUtils.getLoginUserDTO();
        CompanyDataResponse response = new CompanyDataResponse();
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByDeleteIsFalseAndParkUser_Id(loginUserDTO.getId());
        companyDetailOptional.ifPresent(companyDetail -> {
            BeanUtils.copyProperties(companyDetail,response);
            Set<EnclosureTotal> collect = companyDetail.getEnclosureTotals().stream().filter(enclosureTotal -> enclosureTotal.getDelete() == Boolean.FALSE && enclosureTotal.getEnclosureType().equals(EnclosureType.SUPPLEMENT)).collect(Collectors.toSet());
            response.setEnclosureTotals(collect);
        });
        return Result.<CompanyDataResponse>builder().success().data(response).build();
    }

    @Override
    public Result<String> addCompanyDataResponse(CompanyDataResponse companyDataResponse) {
        Optional<CompanyDetail> byId = companyDetailRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(companyDataResponse.getId());
        if(!byId.isPresent()){
            throw new NormalException("信息不存在");
        }
        CompanyDetail companyDetail = byId.get();
        Set<EnclosureTotal> set = companyDataResponse.getEnclosureTotals();
        set.forEach(temp -> {
            Optional<EnclosureTotal> byTemp = enclosureTotalRepository.findById(temp.getId());
            if(!byTemp.isPresent()){
                throw new NormalException("附件不存在");
            }
            EnclosureTotal enclosureTotal = byTemp.get();
            BeanUtils.copyProperties(temp, enclosureTotal);
            enclosureTotalRepository.save(enclosureTotal);
        });
        return Result.<String>builder().success().data("保存成功").build();
    }
}

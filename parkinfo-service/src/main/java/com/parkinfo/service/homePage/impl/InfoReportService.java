package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.InfoReportType;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.response.homePage.BaseReportResponse;
import com.parkinfo.response.homePage.InfoTotalReportResponse;
import com.parkinfo.response.login.ParkInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//获取基本信息报表
@Service
public class InfoReportService {

    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    //获取所有园区
    public List<ParkInfoResponse> findAllPark(){
        List<ParkInfo> all = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<ParkInfoResponse> parkInfoResponses = Lists.newArrayList();
        all.forEach(temp -> {
            if(!temp.getName().equals("总裁园区")){
                ParkInfoResponse response = new ParkInfoResponse();
                BeanUtils.copyProperties(temp, response);
                parkInfoResponses.add(response);
            }
        });
        return parkInfoResponses;
    }

    //入驻百强企业
    public InfoTotalReportResponse getEnterTop(List<ParkInfoResponse> parkInfo){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_TOP.getName());
        List<BaseReportResponse> list = Lists.newArrayList();

        return response;
    }

}

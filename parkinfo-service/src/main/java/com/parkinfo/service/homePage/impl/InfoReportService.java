package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.DefaultEnum;
import com.parkinfo.enums.EnterStatus;
import com.parkinfo.enums.InfoReportType;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.repository.informationTotal.RoomInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.response.homePage.BaseReportResponse;
import com.parkinfo.response.homePage.InfoTotalReportResponse;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.service.archiveInfo.impl.ActivityPaperService;
import com.parkinfo.service.archiveInfo.impl.HonorPaperService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//获取基本信息报表
@Service
public class InfoReportService {

    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private CompanyDetailRepository companyDetailRepository;
    @Autowired
    private EnteredInfoRepository enteredInfoRepository;
    @Autowired
    private RoomInfoRepository roomInfoRepository;
    @Autowired
    private ActivityPaperService activityPaperService;
    @Autowired
    private HonorPaperService honorPaperService;

    //获取所有园区
    public List<ParkInfoResponse> findAllPark(){
        List<ParkInfo> all = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<ParkInfoResponse> parkInfoResponses = Lists.newArrayList();
        all.forEach(temp -> {
            if(!temp.getId().equals(DefaultEnum.CEO_PARK.getDefaultValue())){
                ParkInfoResponse response = new ParkInfoResponse();
                BeanUtils.copyProperties(temp, response);
                parkInfoResponses.add(response);
            }
        });
        return parkInfoResponses;
    }


    //  Map<parkInfo, <CompanyDetail,...,CompanyDetail>>
    public Map<ParkInfoResponse, List<CompanyDetail>> findAllParam(){
        List<ParkInfo> all = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        Map<ParkInfoResponse, List<CompanyDetail>> map = new HashMap<>();
        all.forEach(temp -> {
            if(!temp.getId().equals(DefaultEnum.CEO_PARK.getDefaultValue())){
                ParkInfoResponse response = new ParkInfoResponse();
                BeanUtils.copyProperties(temp, response);
                List<CompanyDetail> list = companyDetailRepository.findByParkInfo_Id(temp.getId());
                map.put(response, list);
            }
        });
        return map;
    }

    //入驻百强企业
    public InfoTotalReportResponse getEnterTop(Map<ParkInfoResponse, List<CompanyDetail>> map){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_TOP.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            List<CompanyDetail> companyDetails = map.get(temp);
            BaseReportResponse reportResponse = new BaseReportResponse();
            reportResponse.setKey(temp.getName());  //key园区名称
            Long count = 0L;
            for(CompanyDetail companyDetail : companyDetails){
                if("是".equals(companyDetail.getHundredCompany())){
                    count++;
                }
            }
            reportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            reportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(reportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //入驻企业
    public InfoTotalReportResponse getAddEnter(Map<ParkInfoResponse, List<CompanyDetail>> map){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ADD_ENTER.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            List<CompanyDetail> companyDetails = map.get(temp);
            BaseReportResponse reportResponse = new BaseReportResponse();
            reportResponse.setKey(temp.getName());  //key园区名称
            Long count = 0L;
            for(CompanyDetail companyDetail : companyDetails){
                if(EnterStatus.ENTERED.equals(companyDetail.getEnterStatus())){
                    count++;
                }
            }
            reportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            reportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(reportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //退出企业
    public InfoTotalReportResponse getExitCompany(Map<ParkInfoResponse, List<CompanyDetail>> map){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.EXIT_COMPANY.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            List<CompanyDetail> companyDetails = map.get(temp);
            BaseReportResponse reportResponse = new BaseReportResponse();
            reportResponse.setKey(temp.getName());  //key园区名称
            Long count = 0L;
            for(CompanyDetail companyDetail : companyDetails){
                if(EnterStatus.LEAVE.equals(companyDetail.getEnterStatus())){
                    count++;
                }
            }
            reportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            reportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(reportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //入园企业产值
    public InfoTotalReportResponse getEnterValue(Map<ParkInfoResponse, List<CompanyDetail>> map){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_VALUE.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            BigDecimal count = new BigDecimal(0);
            List<CompanyDetail> companyDetails = map.get(temp);
            BaseReportResponse reportResponse = new BaseReportResponse();
            reportResponse.setKey(temp.getName());  //key园区名称
            for(CompanyDetail companyDetail : companyDetails){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(companyDetail.getId());
                for (EnteredInfo enteredInfo : all){
                    count.add(enteredInfo.getHalfProduct());
                }
            }
            reportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            reportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(reportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //入园企业税收
    public InfoTotalReportResponse getEnterTax(Map<ParkInfoResponse, List<CompanyDetail>> map){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_TAX.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            BigDecimal count = new BigDecimal(0);
            List<CompanyDetail> companyDetails = map.get(temp);
            BaseReportResponse reportResponse = new BaseReportResponse();
            reportResponse.setKey(temp.getName());  //key园区名称
            for(CompanyDetail companyDetail : companyDetails){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(companyDetail.getId());
                for (EnteredInfo enteredInfo : all){
                    count.add(enteredInfo.getFirstQuarter()).add(enteredInfo.getSecondQuarter()).add(enteredInfo.getThirdQuarter()).add(enteredInfo.getFourthQuarter());
                }
            }
            reportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            reportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(reportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //房间统计
    public InfoTotalReportResponse getParkRoom(List<ParkInfoResponse> parkInfoResponses){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.PARK_ROOM.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        parkInfoResponses.forEach(temp -> {
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(temp.getName());
            List<RoomInfo> byId = roomInfoRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(temp.getId());
            Integer count = 0;
            if(byId != null){
                 count = byId.size();
            }
            baseReportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//            baseReportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(baseReportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //活动类统计
    public InfoTotalReportResponse getActivittTotal(List<ParkInfoResponse> parkInfoResponses){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.ACTIVITY_TOTAL.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        parkInfoResponses.forEach(temp -> {
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(temp.getName());
            List<ArchiveInfo> allActivity = activityPaperService.findAllActivity();
            Integer count = 0;
            if(allActivity != null){
                count = allActivity.size();
            }
            baseReportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1100).setScale(0, BigDecimal.ROUND_HALF_UP);
//            baseReportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(baseReportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }

    //获得的荣誉
    public InfoTotalReportResponse getGetGrade(List<ParkInfoResponse> parkInfoResponses){
        InfoTotalReportResponse response = new InfoTotalReportResponse();
        response.setInfoReportName(InfoReportType.GET_GRADE.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        parkInfoResponses.forEach(temp -> {
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(temp.getName());
            List<ArchiveInfo> allActivity = honorPaperService.findAllActivity();
            Integer count = 0;
            if(allActivity != null){
                count = allActivity.size();
            }
            baseReportResponse.setValue(String.valueOf(count));
//            BigDecimal test = new BigDecimal(Math.random()*1200).setScale(0, BigDecimal.ROUND_HALF_UP);
//            baseReportResponse.setValue(String.valueOf(test));  //测试数据
            list.add(baseReportResponse);
        });
        response.setBaseReportResponses(list);
        return response;
    }


}

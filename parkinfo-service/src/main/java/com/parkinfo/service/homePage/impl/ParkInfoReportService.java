package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.EnterStatus;
import com.parkinfo.enums.InfoReportType;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.repository.informationTotal.RoomInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.response.homePage.BaseReportResponse;
import com.parkinfo.response.homePage.InfoTotalReportResponse;
import com.parkinfo.response.homePage.OrganInfoReportResponse;
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

@Service
//本园区基本信息统计
public class ParkInfoReportService {

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

    //入驻百强企业
    public OrganInfoReportResponse getEnterTop(List<CompanyDetail> companyDetails) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_TOP.getName());
        Long count = 0L;
        for (CompanyDetail companyDetail : companyDetails) {
            if ("是".equals(companyDetail.getHundredCompany())) {
                count++;
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
        return response;
    }

    //入驻企业
    public OrganInfoReportResponse getAddEnter(List<CompanyDetail> companyDetails) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.ADD_ENTER.getName());
        Long count = 0L;
        for (CompanyDetail companyDetail : companyDetails) {
            if (EnterStatus.ENTERED.equals(companyDetail.getEnterStatus())) {
                count++;
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //退出企业
    public OrganInfoReportResponse getExitCompany(List<CompanyDetail> companyDetails) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.EXIT_COMPANY.getName());
        Long count = 0L;
        for (CompanyDetail companyDetail : companyDetails) {
            if (EnterStatus.LEAVE.equals(companyDetail.getEnterStatus())) {
                count++;
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //入园企业产值
    public OrganInfoReportResponse getEnterValue(List<CompanyDetail> companyDetails) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_VALUE.getName());
        List<BaseReportResponse> list = Lists.newArrayList();
        BigDecimal count = new BigDecimal(0);
        for (CompanyDetail companyDetail : companyDetails) {
            List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_IdAndDeleteIsFalse(companyDetail.getId());
            for (EnteredInfo enteredInfo : all) {
                count.add(enteredInfo.getHalfProduct());
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //入园企业税收
    public OrganInfoReportResponse getEnterTax(List<CompanyDetail> companyDetails) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.ENTER_TAX.getName());
        BigDecimal count = new BigDecimal(0);
        for (CompanyDetail companyDetail : companyDetails) {
            List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(companyDetail.getId());
            for (EnteredInfo enteredInfo : all) {
                count.add(enteredInfo.getFirstQuarter()).add(enteredInfo.getSecondQuarter()).add(enteredInfo.getThirdQuarter()).add(enteredInfo.getFourthQuarter());
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //房间统计
    public OrganInfoReportResponse getParkRoom(String parkId) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.PARK_ROOM.getName());
        List<RoomInfo> byId = roomInfoRepository.findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(parkId);
        Integer count = 0;
        if (byId != null) {
            count = byId.size();
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //活动类统计
    public OrganInfoReportResponse getActivittTotal(String parkId) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.ACTIVITY_TOTAL.getName());
        List<ArchiveInfo> allActivity = activityPaperService.findAllActivity();
        Integer count = 0;
        for (ArchiveInfo achiveInfo : allActivity) {
            if (achiveInfo.getParkInfo().getId() == parkId) {
                count++;
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1100).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }

    //获得的荣誉
    public OrganInfoReportResponse getGetGrade(String parkId) {
        OrganInfoReportResponse response = new OrganInfoReportResponse();
        response.setInfoReportName(InfoReportType.GET_GRADE.getName());
        List<ArchiveInfo> allActivity = honorPaperService.findAllActivity();
        Integer count = 0;
        for (ArchiveInfo achiveInfo : allActivity) {
            if (achiveInfo.getParkInfo().getId() == parkId) {
                count++;
            }
        }
        response.setValue(String.valueOf(count));
//        BigDecimal test = new BigDecimal(Math.random() * 1200).setScale(0, BigDecimal.ROUND_HALF_UP);
//        response.setValue(String.valueOf(test));  //测试数据
        return response;
    }
}

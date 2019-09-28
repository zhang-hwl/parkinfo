package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.response.homePage.*;
import com.parkinfo.service.homePage.IHomeRepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class HomeReportServiceImpl implements IHomeRepostService {

    @Autowired
    private CompanyDetailRepository companyDetailRepository;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private EnteredInfoRepository enteredInfoRepository;

    @Override
    public Result<List<EnterReportResponse>> findAllEnterReport() {
        List<EnterReportResponse> result = Lists.newArrayList();
        List<ParkInfo> allPark = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        Calendar calendar = Calendar.getInstance();
        Long enterCount = 0L; //累积入驻企业数
        for(ParkInfo temp : allPark){
            //获取一个园区的统计信息
            Long parkEnterCount = 0L;
            EnterReportResponse response = new EnterReportResponse();
            response.setParkName(temp.getName());
            String parkId = temp.getId();
            List<CompanyDetail> all = companyDetailRepository.findByParkInfo_IdAndEnteredIsTrue(parkId);//获取园区已入驻企业
            List<BaseReportResponse> list = Lists.newArrayList();
            Map<Integer, Integer> map = new HashMap<>();    // <月份,入驻总数>
            for(int i = 1; i <= 12; i++){
                map.put(i, 0);  //初始化数据
            }
            for(CompanyDetail companyDetail : all){
                calendar.setTime(companyDetail.getCreateTime());
                int month = calendar.get(Calendar.MONTH);   //月份
                if(map.containsKey(month)){
                    Integer count = map.get(month);
                    count++;
                    enterCount++;
                    parkEnterCount++;
                    map.put(month, count);
                }
                else{
                    map.put(month, 1);
                }
            }
            map.keySet().forEach(key -> {
                Integer count = map.get(key);
                BaseReportResponse baseReportResponse = new BaseReportResponse();
                baseReportResponse.setKey(String.valueOf(key));
                baseReportResponse.setValue(String.valueOf(count));
                list.add(baseReportResponse);
            });
            response.setEnterCount(String.valueOf(enterCount));
//            response.setParkEnterCount(String.valueOf(parkEnterCount));
            response.setBaseReportResponses(list);
        }
        return Result.<List<EnterReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<TaxReportResponse> findAllYearTaxReport() {
        TaxReportResponse result = new TaxReportResponse();
        List<BaseReportResponse> list = Lists.newArrayList();
        List<ParkInfo> allPark = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        Calendar calendar = Calendar.getInstance();
        BigDecimal allTaxCount = new BigDecimal(0);
        for(ParkInfo parkInfo : allPark){
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(parkInfo.getName());
            BigDecimal taxCount = new BigDecimal(0);   //该税收总数
            Optional<CompanyDetail> byParkUser_id = companyDetailRepository.findByParkUser_Id(parkInfo.getId());
            if(byParkUser_id.isPresent()){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(byParkUser_id.get().getId());
                for (EnteredInfo temp : all) {
                    calendar.setTime(temp.getContinueTime());
                    int year = calendar.get(Calendar.YEAR);
                    calendar.setTime(new Date());
                    int nowYear = calendar.get(Calendar.YEAR);
                    if(year == nowYear){
                        taxCount = temp.getFirstQuarter().add(temp.getSecondQuarter()).add(temp.getThirdQuarter()).add(temp.getFourthQuarter());
                    }
                }
            }
            allTaxCount = allTaxCount.add(taxCount);    //加本园区税收合计
            taxCount.setScale(2, BigDecimal.ROUND_HALF_UP); //取2位小数，四舍五入
            baseReportResponse.setValue(String.valueOf(taxCount));
            list.add(baseReportResponse);
        }
        allTaxCount.setScale(2, BigDecimal.ROUND_HALF_UP);  //取2位小数，四舍五入
        result.setRevenueCount(String.valueOf(allTaxCount));    //累计税收
        result.setBaseReportResponses(list);
        return Result.<TaxReportResponse>builder().success().data(result).build();
    }

    @Override
    public Result<TaxReportResponse> findAllQuarterTaxReport() {
        TaxReportResponse result = new TaxReportResponse();
        List<BaseReportResponse> list = Lists.newArrayList();
        List<ParkInfo> allPark = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        Calendar calendar = Calendar.getInstance();
        BigDecimal allTaxCount = new BigDecimal(0);
        for(ParkInfo parkInfo : allPark){
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(parkInfo.getName());
            BigDecimal taxCount = new BigDecimal(0);   //该税收总数
            Optional<CompanyDetail> byParkUser_id = companyDetailRepository.findByParkUser_Id(parkInfo.getId());
            if(byParkUser_id.isPresent()){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(byParkUser_id.get().getId());
                for (EnteredInfo temp : all) {
                    calendar.setTime(temp.getContinueTime());
                    int year = calendar.get(Calendar.YEAR);
                    calendar.setTime(new Date());
                    int nowYear = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);   //当前月份
                    if(year == nowYear){
                        switch (month){
                            case 1: case 2: case 3:
                                taxCount = temp.getFirstQuarter();
                                break;
                            case 4: case 5: case 6:
                                taxCount = temp.getSecondQuarter();
                                break;
                            case 7: case 8: case 9:
                                taxCount = temp.getThirdQuarter();
                                break;
                            case 10: case 11: case 12:
                                taxCount = temp.getFourthQuarter();
                                break;
                        }
                    }
                }
            }
            allTaxCount = allTaxCount.add(taxCount);    //加本园区税收合计
            taxCount.setScale(2, BigDecimal.ROUND_HALF_UP); //取2位小数，四舍五入
            baseReportResponse.setValue(String.valueOf(taxCount));
            list.add(baseReportResponse);
        }
        allTaxCount.setScale(2, BigDecimal.ROUND_HALF_UP);  //取2位小数，四舍五入
        result.setRevenueCount(String.valueOf(allTaxCount));    //累计税收
        result.setBaseReportResponses(list);
        return Result.<TaxReportResponse>builder().success().data(result).build();
    }

    @Override
    public Result<List<InfoTotalReportResponse>> findAllInfoReport() {
        List<InfoTotalReportResponse> result = Lists.newArrayList();
        return Result.<List<InfoTotalReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BaseReportResponse>> findAllGradeReport() {
        List<BaseReportResponse> result = Lists.newArrayList();
        return Result.<List<BaseReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BaseReportResponse>> findAllIncreaseReport() {
        List<BaseReportResponse> result = Lists.newArrayList();
        return Result.<List<BaseReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BigEventResponse>> findAllBigEventRepost() {
        List<BigEventResponse> result = Lists.newArrayList();
        return Result.<List<BigEventResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport() {
        List<OrganInfoReportResponse> result = Lists.newArrayList();
        return Result.<List<OrganInfoReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoMonthReport() {
        List<OrganInfoReportResponse> result = Lists.newArrayList();
        return Result.<List<OrganInfoReportResponse>>builder().success().data(result).build();
    }
}

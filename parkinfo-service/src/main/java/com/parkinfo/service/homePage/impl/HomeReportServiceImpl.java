package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.EnteredInfoRepository;
import com.parkinfo.repository.informationTotal.BigEventRepository;
import com.parkinfo.response.homePage.*;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.service.homePage.IHomeRepostService;
import com.parkinfo.token.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class HomeReportServiceImpl implements IHomeRepostService {

    @Autowired
    private CompanyDetailRepository companyDetailRepository;
    @Autowired
    private EnteredInfoRepository enteredInfoRepository;
    @Autowired
    private BigEventRepository bigEventRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private InfoReportService infoReportService;
    @Autowired
    private ParkInfoReportService parkInfoReportService;

    @Override
    public Result<List<EnterReportResponse>> findAllEnterReport() {
        List<EnterReportResponse> result = Lists.newArrayList();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        Calendar calendar = Calendar.getInstance();
        Long enterCount = 0L; //累积入驻企业数
        for(ParkInfoResponse temp : allPark){

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
            for(int i = 1; i <= 12; i++){
                int test = (int)(Math.random()*1000);
                map.put(i, test);            //测试数据
                enterCount += test;
            }
            map.keySet().forEach(key -> {
                Integer count = map.get(key);
                BaseReportResponse baseReportResponse = new BaseReportResponse();
                baseReportResponse.setKey(String.valueOf(key));
                baseReportResponse.setValue(String.valueOf(count));
                list.add(baseReportResponse);
            });
            response.setBaseReportResponses(list);
            result.add(response);
        }
        int len = result.size();
        for(int i = 0; i < len; i++){
            EnterReportResponse response = result.get(i);
            response.setEnterCount(String.valueOf(enterCount));
            result.set(i, response);
        }
        return Result.<List<EnterReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<TaxReportResponse> findAllYearTaxReport() {
        TaxReportResponse result = new TaxReportResponse();
        List<BaseReportResponse> list = Lists.newArrayList();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        Calendar calendar = Calendar.getInstance();
        BigDecimal allTaxCount = new BigDecimal(0);
        for(ParkInfoResponse parkInfo : allPark){
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(parkInfo.getName());
            BigDecimal taxCount = new BigDecimal(0);   //该税收总数
            List<CompanyDetail> byParkUser_id = companyDetailRepository.findByParkInfo_Id(parkInfo.getId());
            for(CompanyDetail detail : byParkUser_id){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(detail.getId());
                for (EnteredInfo temp : all) {
                    calendar.setTime(temp.getContinueTime());
                    int year = calendar.get(Calendar.YEAR);
                    calendar.setTime(new Date());
                    int nowYear = calendar.get(Calendar.YEAR);
                    if(year == nowYear){
                        taxCount = taxCount.add(temp.getFirstQuarter()).add(temp.getSecondQuarter()).add(temp.getThirdQuarter()).add(temp.getFourthQuarter());
                    }
                }
            }
            //测试数据
            taxCount = new BigDecimal((int)(Math.random()*1000));
            //
            allTaxCount = allTaxCount.add(taxCount);    //加本园区税收合计
            taxCount.setScale(0, BigDecimal.ROUND_HALF_UP);
            baseReportResponse.setValue(String.valueOf(taxCount));
            list.add(baseReportResponse);
        }
        allTaxCount.setScale(0, BigDecimal.ROUND_HALF_UP);
        result.setRevenueCount(String.valueOf(allTaxCount));    //累计税收
        result.setBaseReportResponses(list);
        return Result.<TaxReportResponse>builder().success().data(result).build();
    }

    @Override
    public Result<TaxReportResponse> findAllQuarterTaxReport() {
        TaxReportResponse result = new TaxReportResponse();
        List<BaseReportResponse> list = Lists.newArrayList();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        Calendar calendar = Calendar.getInstance();
        BigDecimal allTaxCount = new BigDecimal(0);
        for(ParkInfoResponse parkInfo : allPark){
            BaseReportResponse baseReportResponse = new BaseReportResponse();
            baseReportResponse.setKey(parkInfo.getName());
            BigDecimal taxCount = new BigDecimal(0);   //该税收总数
            List<CompanyDetail> byParkUser_id = companyDetailRepository.findByParkInfo_Id(parkInfo.getId());
            for(CompanyDetail detail : byParkUser_id){
                List<EnteredInfo> all = enteredInfoRepository.findAllByCompanyDetail_Id(detail.getId());
                for (EnteredInfo temp : all) {
                    calendar.setTime(temp.getContinueTime());
                    int year = calendar.get(Calendar.YEAR);
                    calendar.setTime(new Date());
                    int nowYear = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);   //当前月份
                    if(year == nowYear){
                        switch (month){
                            case 1: case 2: case 3:
                                taxCount = taxCount.add(temp.getFirstQuarter());
                                break;
                            case 4: case 5: case 6:
                                taxCount = taxCount.add(temp.getSecondQuarter());
                                break;
                            case 7: case 8: case 9:
                                taxCount = taxCount.add(temp.getThirdQuarter());
                                break;
                            case 10: case 11: case 12:
                                taxCount = taxCount.add(temp.getFourthQuarter());
                                break;
                        }
                    }
                }
            }
            //测试数据
            taxCount = new BigDecimal((int)(Math.random()*1000));
            //
            allTaxCount = allTaxCount.add(taxCount);    //加本园区税收合计
            taxCount.setScale(0, BigDecimal.ROUND_HALF_UP); //四舍五入
            baseReportResponse.setValue(String.valueOf(taxCount));
            list.add(baseReportResponse);
        }
        allTaxCount.setScale(0, BigDecimal.ROUND_HALF_UP);  //四舍五入
        result.setRevenueCount(String.valueOf(allTaxCount));    //累计税收
        result.setBaseReportResponses(list);
        return Result.<TaxReportResponse>builder().success().data(result).build();
    }

    @Override
    public Result<List<InfoTotalReportResponse>> findAllInfoReport() {
        List<InfoTotalReportResponse> result = Lists.newArrayList();
        Map<ParkInfoResponse, List<CompanyDetail>> map = infoReportService.findAllParam();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        result.add(infoReportService.getEnterTop(map)); //入驻百强企业
        result.add(infoReportService.getAddEnter(map)); //入园企业
        result.add(infoReportService.getExitCompany(map)); //离园企业
        result.add(infoReportService.getEnterValue(map));   //入园产值统计
        result.add(infoReportService.getEnterTax(map));   //入园税收统计
        result.add(infoReportService.getParkRoom(allPark));  //园区房间统计
        result.add(infoReportService.getActivittTotal(allPark));  //活动类统计
        result.add(infoReportService.getGetGrade(allPark));  //获得的荣誉
        return Result.<List<InfoTotalReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BaseReportResponse>> findAllGradeReport() {
        List<BaseReportResponse> result = Lists.newArrayList();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        allPark.forEach(temp -> {
            BaseReportResponse response = new BaseReportResponse();
            response.setKey(temp.getName());    //横坐标--园区名称
            BigDecimal taxCount = new BigDecimal(0);   //该税收总数
            List<CompanyDetail> byId = companyDetailRepository.findByParkInfo_Id(temp.getId());
            for(CompanyDetail companyDetail : byId){
                List<EnteredInfo> allEnteredInfo = enteredInfoRepository.findAllByCompanyDetail_Id(companyDetail.getId());
                for (EnteredInfo enteredInfo : allEnteredInfo) {
                    taxCount = taxCount.add(enteredInfo.getFirstQuarter()).add(enteredInfo.getSecondQuarter()).add(enteredInfo.getThirdQuarter()).add(enteredInfo.getFourthQuarter());
                }
            }
            taxCount.setScale(0, BigDecimal.ROUND_HALF_UP);
            response.setValue(String.valueOf(taxCount));    //纵坐标--税收合计
            response.setValue((int)(Math.random()*100)+"");    //测试数据
            result.add(response);
        });
        return Result.<List<BaseReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BaseReportResponse>> findAllIncreaseReport() {
        List<BaseReportResponse> result = Lists.newArrayList();
        return Result.<List<BaseReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BigEventReportResponse>> findAllBigEventReport() {
        List<BigEventResponse> result = Lists.newArrayList();
        List<ParkInfoResponse> allPark = infoReportService.findAllPark();
        allPark.forEach(tempPark -> {
            List<BigEvent> byId = bigEventRepository.findAllOrderAndPark(tempPark.getId());
            byId.forEach(tempBigEvent -> {
                BigEventResponse response = new BigEventResponse();
                response.setParkName(tempPark.getName());
                BeanUtils.copyProperties(tempBigEvent, response);
                response.setYear(String.valueOf(tempBigEvent.getYear()));
                response.setMonth(String.valueOf(tempBigEvent.getMonth()));
                result.add(response);
            });
        });
        Map<String, List<BigEventInnerResponse>> map = new TreeMap<>();
        result.forEach(temp -> {
            if(map.containsKey(temp.getYear())){
                List<BigEventInnerResponse> bigEventInnerResponses = map.get(temp.getYear());
                BigEventInnerResponse bigEventInnerResponse = new BigEventInnerResponse();
                BeanUtils.copyProperties(temp, bigEventInnerResponse);
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                bigEventInnerResponse.setId(id);
                bigEventInnerResponses.add(bigEventInnerResponse);
            }
            else{
                List<BigEventInnerResponse> bigEventInnerResponses = Lists.newArrayList();
                BigEventInnerResponse bigEventInnerResponse = new BigEventInnerResponse();
                BeanUtils.copyProperties(temp, bigEventInnerResponse);
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                bigEventInnerResponse.setId(id);
                bigEventInnerResponses.add(bigEventInnerResponse);
                map.put(temp.getYear(), bigEventInnerResponses);
            }
        });
        List<BigEventReportResponse> responses = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            BigEventReportResponse bigEventReportResponse = new BigEventReportResponse();
            bigEventReportResponse.setYear(temp);
            bigEventReportResponse.setBigEventInnerResponses(map.get(temp));
            responses.add(bigEventReportResponse);
        });
        return Result.<List<BigEventReportResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport() {
        List<OrganInfoReportResponse> result = Lists.newArrayList();
        String parkId = tokenUtils.getCurrentParkInfo().getId();
        List<CompanyDetail> list = companyDetailRepository.findByParkInfo_Id(parkId);
        result.add(parkInfoReportService.getEnterTop(list));    //入驻百强企业
        result.add(parkInfoReportService.getAddEnter(list));    //入驻企业
        result.add(parkInfoReportService.getExitCompany(list)); //退出企业
        result.add(parkInfoReportService.getEnterValue(list));  //入园企业产值
        result.add(parkInfoReportService.getEnterTax(list));    //入园企业税收
        result.add(parkInfoReportService.getParkRoom(parkId));  //本园区房间统计
        result.add(parkInfoReportService.getActivittTotal(parkId)); //活动类统计
        result.add(parkInfoReportService.getGetGrade(parkId));  //成绩统计
        return Result.<List<OrganInfoReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoMonthReport() {
        List<OrganInfoReportResponse> result = Lists.newArrayList();
        return Result.<List<OrganInfoReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<BigEventReportResponse>> findAllOfficeBigEventReport() {
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        if(parkInfo == null){
            throw new NormalException("token不存在或已失效");
        }
        String parkInfoId = parkInfo.getId();
        List<BigEvent> allOrderAndPark = bigEventRepository.findAllOrderAndPark(parkInfoId);
        List<BigEventResponse> result = Lists.newArrayList();
        allOrderAndPark.forEach(temp -> {
            BigEventResponse response = new BigEventResponse();
            BeanUtils.copyProperties(temp, response);
            response.setYear(String.valueOf(temp.getYear()));
            response.setMonth(String.valueOf(temp.getMonth()));
            response.setParkName(parkInfo.getName());
            result.add(response);
        });
        Map<String, List<BigEventInnerResponse>> map = new TreeMap<>();
        result.forEach(temp -> {
            if(map.containsKey(temp.getYear())){
                List<BigEventInnerResponse> bigEventInnerResponses = map.get(temp.getYear());
                BigEventInnerResponse bigEventInnerResponse = new BigEventInnerResponse();
                BeanUtils.copyProperties(temp, bigEventInnerResponse);
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                bigEventInnerResponse.setId(id);
                bigEventInnerResponses.add(bigEventInnerResponse);
            }
            else{
                List<BigEventInnerResponse> bigEventInnerResponses = Lists.newArrayList();
                BigEventInnerResponse bigEventInnerResponse = new BigEventInnerResponse();
                BeanUtils.copyProperties(temp, bigEventInnerResponse);
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                bigEventInnerResponse.setId(id);
                bigEventInnerResponses.add(bigEventInnerResponse);
                map.put(temp.getYear(), bigEventInnerResponses);
            }
        });
        List<BigEventReportResponse> responses = Lists.newArrayList();
        map.keySet().forEach(temp -> {
            BigEventReportResponse bigEventReportResponse = new BigEventReportResponse();
            bigEventReportResponse.setYear(temp);
            bigEventReportResponse.setBigEventInnerResponses(map.get(temp));
            responses.add(bigEventReportResponse);
        });
        return Result.<List<BigEventReportResponse>>builder().success().data(responses).build();
    }
}

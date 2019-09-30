package com.parkinfo.service.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.response.homePage.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface IHomeRepostService {

    //获取所有园区已入驻企业累计
    Result<List<EnterReportResponse>> findAllEnterReport();

    //获取园区当年税收累计
    Result<TaxReportResponse> findAllYearTaxReport();

    //获取园区当季度税收累计
    Result<TaxReportResponse> findAllQuarterTaxReport();

    //获取园区基本信息统计
    Result<List<InfoTotalReportResponse>> findAllInfoReport();

    //获取各园区税收成绩数据展示
    Result<List<BaseReportResponse>> findAllGradeReport();

    //获取园区税收数据增长比例
    Result<List<BaseReportResponse>> findAllIncreaseReport();

    //获取园区大事记
    Result<List<BigEventReportResponse>> findAllBigEventReport();

    //政府-获取园区当年基本信息
    Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport();

    //政府-获取园区当月基本信息
    Result<List<OrganInfoReportResponse>> findAllOrganInfoMonthReport();

    //获取政府园区大事记
    Result<List<BigEventReportResponse>> findAllOfficeBigEventReport();
}

package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.response.homePage.*;
import com.parkinfo.service.homePage.IHomeRepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeReportServiceImpl implements IHomeRepostService {



    @Override
    public Result<List<EnterReportResponse>> findAllEnterReport() {
        List<EnterReportResponse> result = Lists.newArrayList();
        return Result.<List<EnterReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<TaxReportResponse>> findAllYearTaxReport() {
        List<TaxReportResponse> result = Lists.newArrayList();
        return Result.<List<TaxReportResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<TaxReportResponse>> findAllMonthTaxReport() {
        List<TaxReportResponse> result = Lists.newArrayList();
        return Result.<List<TaxReportResponse>>builder().success().data(result).build();
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

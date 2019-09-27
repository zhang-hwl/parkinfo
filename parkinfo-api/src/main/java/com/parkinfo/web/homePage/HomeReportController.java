package com.parkinfo.web.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.response.homePage.*;
import com.parkinfo.service.homePage.IHomeRepostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/report")
@Api(value = "/home/report", tags = {"首页-报表"})
public class HomeReportController {

    @Autowired
    private IHomeRepostService iHomeRepostService;

    @PostMapping("/enter/findAll")
    @ApiOperation(value = "总裁-获取所有园区已入驻企业累计")
    public Result<List<EnterReportResponse>> findAllEnterReport(){
        return iHomeRepostService.findAllEnterReport();
    }

    @PostMapping("/tax/find/year")
    @ApiOperation(value = "总裁-获取园区当年税收累计")
    public Result<List<TaxReportResponse>> findAllYearTaxReport(){
        return iHomeRepostService.findAllYearTaxReport();
    }

    @PostMapping("/tax/find/month")
    @ApiOperation(value = "总裁-获取园区当月税收累计")
    public Result<List<TaxReportResponse>> findAllMonthTaxReport(){
        return iHomeRepostService.findAllMonthTaxReport();
    }

    @PostMapping("/info/findAll")
    @ApiOperation(value = "总裁-获取园区基本信息统计")
    public Result<List<InfoTotalReportResponse>> findAllInfoReport(){
        return iHomeRepostService.findAllInfoReport();
    }

    @PostMapping("/grade/findAll")
    @ApiOperation(value = "总裁-获取各园区成绩数据展示")
    public Result<List<BaseReportResponse>> findAllGradeReport(){
        return iHomeRepostService.findAllGradeReport();
    }

    @PostMapping("/increase/findAll")
    @ApiOperation(value = "总裁-获取园区数据增长比例")
    public Result<List<BaseReportResponse>> findAllIncreaseReport(){
        return iHomeRepostService.findAllIncreaseReport();
    }

    @PostMapping("/bigEvent/findAll")
    @ApiOperation(value = "总裁-获取园区大事记")
    public Result<List<BigEventResponse>> findAllBigEventRepost(){
        return iHomeRepostService.findAllBigEventRepost();
    }

    @PostMapping("/organ/info/find/year")
    @ApiOperation(value = "政府-获取园区当年基本信息")
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport(){
        return iHomeRepostService.findAllOrganInfoYearReport();
    }

    @PostMapping("/organ/info/find/month")
    @ApiOperation(value = "政府-获取园区当月基本信息")
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoMonthReport(){
        return iHomeRepostService.findAllOrganInfoMonthReport();
    }

}

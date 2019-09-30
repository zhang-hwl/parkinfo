package com.parkinfo.web.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.enums.TimeEnum;
import com.parkinfo.request.homePage.ReportRequest;
import com.parkinfo.response.homePage.*;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.homePage.IHomeRepostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("/tax/find")
    @ApiOperation(value = "总裁-获取园区税收累计")
    public Result<TaxReportResponse> findAllYearTaxReport(@RequestBody @Valid ReportRequest reportRequest, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<TaxReportResponse>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        if(reportRequest.getTimeEnum().equals(TimeEnum.YEAR)){
            return iHomeRepostService.findAllYearTaxReport();
        }
        else{
            return iHomeRepostService.findAllQuarterTaxReport();
        }
    }

    @PostMapping("/info/findAll")
    @ApiOperation(value = "总裁-获取园区基本信息统计")
    public Result<List<InfoTotalReportResponse>> findAllInfoReport(){
        return iHomeRepostService.findAllInfoReport();
    }

    @PostMapping("/grade/findAll")
    @ApiOperation(value = "总裁-获取各园区税收成绩数据展示")
    public Result<List<BaseReportResponse>> findAllGradeReport(){
        return iHomeRepostService.findAllGradeReport();
    }

    @PostMapping("/increase/findAll")
    @ApiOperation(value = "总裁-获取园区税收数据增长比例")
    public Result<List<BaseReportResponse>> findAllIncreaseReport(){
        return iHomeRepostService.findAllIncreaseReport();
    }

    @PostMapping("/bigEvent/findAll")
    @ApiOperation(value = "总裁-获取园区大事记")
    public Result<List<BigEventReportResponse>> findAllBigEventReport(){
        return iHomeRepostService.findAllBigEventReport();
    }

    @PostMapping("/organ/info/find")
    @ApiOperation(value = "政府-获取园区基本信息")
    public Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport(@RequestBody @Valid ReportRequest reportRequest, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<List<OrganInfoReportResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        if(reportRequest.getTimeEnum().equals(TimeEnum.YEAR)){
            return iHomeRepostService.findAllOrganInfoYearReport();
        }
        else{
            return iHomeRepostService.findAllOrganInfoYearReport();
        }
    }

    @PostMapping("/bigEvent/find/office")
    @ApiOperation(value = "政府-获取园区大事记")
    public Result<List<BigEventReportResponse>> findAllOfficeBigEventReport(){
        return iHomeRepostService.findAllOfficeBigEventReport();
    }

}

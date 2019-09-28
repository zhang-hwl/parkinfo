package com.parkinfo.service.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.response.homePage.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface IHomeRepostService {

    Result<List<EnterReportResponse>> findAllEnterReport();

    Result<TaxReportResponse> findAllYearTaxReport();

    Result<TaxReportResponse> findAllQuarterTaxReport();

    Result<List<InfoTotalReportResponse>> findAllInfoReport();

    Result<List<BaseReportResponse>> findAllGradeReport();

    Result<List<BaseReportResponse>> findAllIncreaseReport();

    Result<List<BigEventResponse>> findAllBigEventRepost();

    Result<List<OrganInfoReportResponse>> findAllOrganInfoYearReport();

    Result<List<OrganInfoReportResponse>> findAllOrganInfoMonthReport();
}

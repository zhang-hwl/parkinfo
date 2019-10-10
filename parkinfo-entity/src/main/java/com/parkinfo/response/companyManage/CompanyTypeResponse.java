package com.parkinfo.response.companyManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:44
 */
@Data
public class CompanyTypeResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("下级分类")
    private List<CompanyTypeDetailResponse> kind;



}

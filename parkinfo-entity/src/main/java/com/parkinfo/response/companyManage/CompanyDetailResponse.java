package com.parkinfo.response.companyManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Li
 * description:
 * date: 2019-10-11 15:59
 */
@Data
public class CompanyDetailResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("企业名称")
    private String name;

}

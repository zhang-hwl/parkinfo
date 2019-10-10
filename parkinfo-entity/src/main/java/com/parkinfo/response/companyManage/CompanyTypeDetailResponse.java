package com.parkinfo.response.companyManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:46
 */
@Data
public class CompanyTypeDetailResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

}

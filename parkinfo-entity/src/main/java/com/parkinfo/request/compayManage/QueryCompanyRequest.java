package com.parkinfo.request.compayManage;

import com.parkinfo.enums.CheckStatus;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryCompanyRequest extends PageRequest {
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "审核状态")
    @Enumerated(EnumType.ORDINAL)  //APPLYING,AGREE,REFUSE
    private CheckStatus checkStatus;
}
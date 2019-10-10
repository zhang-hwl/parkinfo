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

    @ApiModelProperty(value = "园区id")
    private String parkId;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "审核状态")
    @Enumerated(EnumType.ORDINAL)  //APPLYING,AGREE,REFUSE
    private CheckStatus checkStatus;

    @ApiModelProperty(value = "小类id")
    private String typeId;
}

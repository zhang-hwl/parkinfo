package com.parkinfo.request.compayManage;

import com.parkinfo.enums.EnterStatus;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryEnterRequest extends PageRequest {
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "入驻状态")
    @Enumerated(EnumType.ORDINAL)//ENTERED,LEAVE  已入住,已离园
    private EnterStatus enterStatus;
}

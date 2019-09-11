package com.parkinfo.response.companyManage;

import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class EnterResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "续约次数")
    private Integer number;

    @ApiModelProperty(value = "入驻状态")
    @Enumerated(EnumType.ORDINAL)//ENTERED,LEAVE  已入住,已离园
    private EnterStatus enterStatus;

}

package com.parkinfo.response.parkService;

import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuseType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class BusinessAmuseResponse {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("企业类型  小类")
    private BusinessAmuseType type;

    @ApiModelProperty("企业logo")
    private String logo;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;
}

package com.parkinfo.request.compayManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SetEnterDetailRequest {

    @ApiModelProperty(value = "入驻信息id")
    private String enterId;

    @ApiModelProperty(value = "入驻面积")
    private String enterArea;

    @ApiModelProperty(value = "人员规模")
    private Integer peopleNum;

    @ApiModelProperty(value = "第几次续约")
    private String continueNext;

    @ApiModelProperty(value = "续约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date continueTime;

    @ApiModelProperty(value = "上半年税收")
    private BigDecimal halfTax;

    @ApiModelProperty(value = "上半年产值")
    private BigDecimal halfProduct;

    @ApiModelProperty(value = "本年预计税收")
    private BigDecimal predictTax;

    @ApiModelProperty(value = "一月税收")
    private BigDecimal januaryTax;

    @ApiModelProperty(value = "二月月税收")
    private BigDecimal februaryTax;

    @ApiModelProperty(value = "三月税收")
    private BigDecimal marchTax;

    @ApiModelProperty(value = "四月税收")
    private BigDecimal aprilTax;

    @ApiModelProperty(value = "五月税收")
    private BigDecimal mayTax;

    @ApiModelProperty(value = "六月税收")
    private BigDecimal juneTax;

    @ApiModelProperty(value = "七月税收")
    private BigDecimal julyTax;

    @ApiModelProperty(value = "八月税收")
    private BigDecimal augustTax;

    @ApiModelProperty(value = "九月税收")
    private BigDecimal septTax;

    @ApiModelProperty(value = "十月税收")
    private BigDecimal octTax;

    @ApiModelProperty(value = "十一月税收")
    private BigDecimal novTax;

    @ApiModelProperty(value = "十二月税收")
    private BigDecimal decTax;
}

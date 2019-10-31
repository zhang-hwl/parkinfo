package com.parkinfo.request.compayManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddEnterDetailRequest {

    @ApiModelProperty(value = "入驻房间号")
    @NotBlank(message = "入住房间号不能为空")
    private String roomNum;

    @ApiModelProperty(value = "入驻面积")
    @NotBlank(message = "入住面积不能为空")
    private String enterArea;

    @ApiModelProperty(value = "人员规模")
    @NotNull(message = "人员规模不能为空")
    private Integer peopleNum;

    @ApiModelProperty(value = "第几次续约")
    private String continueNext;

    @ApiModelProperty(value = "续约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @NotNull(message = "续约时间不能为空")
    private Date continueTime;

    @ApiModelProperty(value = "上半年税收")
    private BigDecimal halfTax;

    @ApiModelProperty(value = "上半年产值")
    private BigDecimal halfProduct;

    @ApiModelProperty(value = "本年预计税收")
    private BigDecimal predictTax;

    @ApiModelProperty(value = "本年第一季度税收")
    private BigDecimal firstQuarter;

    @ApiModelProperty(value = "本年第二季度税收")
    private BigDecimal secondQuarter;

    @ApiModelProperty(value = "本年第三季度税收")
    private BigDecimal thirdQuarter;

    @ApiModelProperty(value = "本年第四季度税收")
    private BigDecimal fourthQuarter;
}

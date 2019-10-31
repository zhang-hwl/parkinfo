package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
public class CompeteGradenInfoRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    @NotBlank(message = "版本标签不能为空")
    private String version;

    @Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;

    @Excel(name = "项目", width = 15)
    @ApiModelProperty(value = "项目")
    @NotBlank(message = "项目不能为空")
    private String project;

    @Excel(name = "地方留存比例(%)", width = 15)
    @ApiModelProperty(value = "地方留存比例")
    //可以为字符串，例如有，无，待补充
    private String remainRatio;

    @Excel(name = "入驻第一年(%)", width = 15)
    @ApiModelProperty(value = "入驻第一年")
    private String enterOne;

    @Excel(name = "入驻第二年(%)", width = 15)
    @ApiModelProperty(value = "入驻第二年")
    private String enterTwo;

    @Excel(name = "入驻第三年(%)", width = 15)
    @ApiModelProperty(value = "入驻第三年")
    private String enterThree;

    @Excel(name = "入驻第四年(%)", width = 15)
    @ApiModelProperty(value = "入驻第四年")
    private String enterFour;

    @Excel(name = "入驻第五年(%)", width = 15)
    @ApiModelProperty(value = "入驻第五年")
    private String enterFive;

    @Excel(name = "说明", width = 30)
    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "关联园区")
    @NotBlank(message = "所属园区不能为空")
    private ParkInfoResponse parkInfoResponse;

}

package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CheckRecordRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    @NotBlank(message = "版本标签不能为空")
    private String version;

    @ApiModelProperty(value = "分类")
    @NotBlank(message = "分类不能为空")
    private String classification;

    @ApiModelProperty(value = "运营标准事项")
    private String operating;

    @ApiModelProperty(value = "点检情况")
    private String checkStatus;

    @ApiModelProperty(value = "建议事项")
    private String suggest;

    @ApiModelProperty(value = "点检人")
    private String checkPerson;

    @ApiModelProperty(value = "点检时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;

    @ApiModelProperty(value = "关联园区")
    private ParkInfoResponse parkInfoResponse;

}

package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.ProjectApplyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectApplyRecordResponse {
    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyDate;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系电话")
    private String contactNumber;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty(value = "申请状态",allowableValues = "APPLYING,FINISHED,REFUSED")
    private ProjectApplyStatus status;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目类型")
    private String projectType;

    @ApiModelProperty("项目奖励")
    private String projectAward;

    @ApiModelProperty("项目类型Id")
    private String typeId;
}

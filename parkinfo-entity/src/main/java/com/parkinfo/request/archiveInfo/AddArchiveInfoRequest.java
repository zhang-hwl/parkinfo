package com.parkinfo.request.archiveInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddArchiveInfoRequest {

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件大类,前端不传")
    private String general;

    @ApiModelProperty(value = "文件种类")
    @NotBlank(message = "文件分类不能为空")
    private String kind;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    @NotBlank(message = "文件不能为空")
    private String fileAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否对外")
    @NotNull(message = "是否对外不能为空")
    private Boolean external;

    @ApiModelProperty(value = "本园区员工是否有查看权限")
    @NotNull(message = "本园区员工是否有查看权限不能为空")
    private Boolean parkPerson;

    @ApiModelProperty(value = "其他园区员工是否有查看权限")
    @NotNull(message = "其他园区员工是否有查看权限不能为空")
    private Boolean otherParkPerson;

    @ApiModelProperty(value = "政府官员是否有查看权限")
    @NotNull(message = "政府官员是否有查看权限不能为空")
    private Boolean government;

    @ApiModelProperty(value = "HR机构是否有查询权限")
    @NotNull(message = "HR机构是否有查询权限不能为空")
    private Boolean hrOrgan;


}

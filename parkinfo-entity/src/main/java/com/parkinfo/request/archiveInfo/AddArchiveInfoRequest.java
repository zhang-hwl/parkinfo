package com.parkinfo.request.archiveInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddArchiveInfoRequest {

    @ApiModelProperty(value = "文件大类")
    private String general;

    @ApiModelProperty(value = "文件种类")
    private String kind;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String fileAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否对外")
    private Boolean external;


}

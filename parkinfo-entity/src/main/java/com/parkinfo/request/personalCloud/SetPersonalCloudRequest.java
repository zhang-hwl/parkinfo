package com.parkinfo.request.personalCloud;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SetPersonalCloudRequest {

    @ApiModelProperty(value = "文件id")
    private String id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "备注")
    private String remark;
}

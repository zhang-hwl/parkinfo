package com.parkinfo.request.infoTotalRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InfoVersionResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本名称")
    private String version;

    @ApiModelProperty(value = "大类名称")
    private String general;

}

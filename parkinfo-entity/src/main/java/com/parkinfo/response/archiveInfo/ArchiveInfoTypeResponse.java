package com.parkinfo.response.archiveInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ArchiveInfoTypeResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型名称")
    private String type;

}

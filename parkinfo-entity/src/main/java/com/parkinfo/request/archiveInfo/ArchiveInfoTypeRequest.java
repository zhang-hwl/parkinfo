package com.parkinfo.request.archiveInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArchiveInfoTypeRequest {

    @ApiModelProperty(value = "大类id")
    private String generalId;

    @ApiModelProperty(value = "大类名称")
    private String generalName;

    @ApiModelProperty(value = "小类Id")
    private String kindId;

    @ApiModelProperty(value = "小类名称")
    private String kindName;

}

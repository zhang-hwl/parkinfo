package com.parkinfo.response.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectApplyRecordTypeResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("类型名称")
    private String type;

}

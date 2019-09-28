package com.parkinfo.request.parkService.learningData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LearnDataTypeRequest {

    @ApiModelProperty("id")
    private String generalId;

    @ApiModelProperty("类型名称")
    private String generalName;

    @ApiModelProperty("小类id")
    private String kindId;

    @ApiModelProperty("小类名称")
    private String kindName;

}

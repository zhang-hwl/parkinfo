package com.parkinfo.request.parkService.commonServiceWindow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonServiceWindowTypeRequest {

    @ApiModelProperty("id")
    private String generalId;

    @ApiModelProperty("类型名称")
    private String generalName;

    @ApiModelProperty("小类id")
    private String kindId;

    @ApiModelProperty("小类名称")
    private String kindName;

}

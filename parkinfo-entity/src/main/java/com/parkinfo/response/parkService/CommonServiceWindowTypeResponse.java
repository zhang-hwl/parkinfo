package com.parkinfo.response.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommonServiceWindowTypeResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("类型名称")
    private String type;

    @ApiModelProperty("小类")
    List<CommonServiceWindowTypeResponse> kind;

}

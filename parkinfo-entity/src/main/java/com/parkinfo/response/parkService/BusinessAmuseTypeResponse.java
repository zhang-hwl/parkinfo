package com.parkinfo.response.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BusinessAmuseTypeResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型")
    private String type;

//    @ApiModelProperty(value = "小类")
//    private List<BusinessAmuseTypeResponse> kind;


}

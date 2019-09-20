package com.parkinfo.request.archiveInfo;


import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryLearnDataInfoRequest extends PageRequest {

    @ApiModelProperty(value = "大类id")
    private String general;

    @ApiModelProperty(value = "小类id")
    private String kind;

}

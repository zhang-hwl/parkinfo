package com.parkinfo.response.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LearnDataKindResponse {

    @ApiModelProperty
    private String id;

    private String type;
}

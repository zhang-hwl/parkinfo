package com.parkinfo.response.parkService;

import com.parkinfo.entity.parkService.learningData.LearnDataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LearnDataTypeResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("小类")
    private List<LearnDataKindResponse> kind;

}

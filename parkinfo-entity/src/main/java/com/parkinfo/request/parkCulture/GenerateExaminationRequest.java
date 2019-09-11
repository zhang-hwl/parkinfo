package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 15:01
 **/
@Data
public class GenerateExaminationRequest {

    @ApiModelProperty(value = "试卷名称")
    private String name;

    @ApiModelProperty(value = "答题时间（分钟）")
    private Integer time;

    @ApiModelProperty(value = "试题")
    private List<GenerateQuestionRequest> questions;

    @ApiModelProperty(value = "人员Id")
    private List<String> userIds;
}

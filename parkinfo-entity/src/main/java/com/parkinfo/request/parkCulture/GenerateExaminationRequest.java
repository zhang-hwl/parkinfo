package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @Min(message = "开始时间应大于0",value = 0)
    private Integer time;

    @ApiModelProperty(value = "试题")
    private List<GenerateQuestionRequest> questions;

    @ApiModelProperty(value = "人员Id")
    @NotNull(message = "考试人员不能为空")
    private List<String> userIds;
}

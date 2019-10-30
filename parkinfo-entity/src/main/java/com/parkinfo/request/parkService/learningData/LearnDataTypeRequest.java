package com.parkinfo.request.parkService.learningData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class LearnDataTypeRequest {

    @ApiModelProperty("id")
    private String generalId;

    @ApiModelProperty("类型名称")
    @Length(max = 9,message = "不能超过9个字符")
    @NotBlank(message = "类型名称不能为空")
    private String generalName;

    @ApiModelProperty("小类id")
    private String kindId;

    @ApiModelProperty("小类名称")
    @Length(max = 9,message = "不能超过9个字符")
    private String kindName;

}

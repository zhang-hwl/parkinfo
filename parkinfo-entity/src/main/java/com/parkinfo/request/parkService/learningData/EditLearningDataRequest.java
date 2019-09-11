package com.parkinfo.request.parkService.learningData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditLearningDataRequest extends AddLearningDataRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}

package com.parkinfo.request.parkService.commonServiceWindow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditCommonServiceWindowRequest extends AddCommonServiceWindowRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}

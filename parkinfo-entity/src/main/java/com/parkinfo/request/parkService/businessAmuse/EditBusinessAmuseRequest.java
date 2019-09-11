package com.parkinfo.request.parkService.businessAmuse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditBusinessAmuseRequest extends AddBusinessAmuseRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}

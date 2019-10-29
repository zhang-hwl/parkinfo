package com.parkinfo.request.parkService.businessAmuse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddBusinessAmuseTypeRequest {

    //类型
    @NotBlank(message = "类型名称不能为空")
    private String type;
}

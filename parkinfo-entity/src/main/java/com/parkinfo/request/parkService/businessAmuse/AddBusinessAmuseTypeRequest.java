package com.parkinfo.request.parkService.businessAmuse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddBusinessAmuseTypeRequest {

    //类型
    private String type;
}

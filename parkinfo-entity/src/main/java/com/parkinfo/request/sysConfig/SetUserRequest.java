package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetUserRequest extends AddUserRequest{

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

}

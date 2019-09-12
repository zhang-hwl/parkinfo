package com.parkinfo.request.login;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserByParkRequest extends PageRequest {

    @ApiModelProperty("园区id")
    private String id;

}

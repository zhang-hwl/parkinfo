package com.parkinfo.request.sysConfig;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysParkRequest extends PageRequest {

    @ApiModelProperty("园区名称")
    private String parkName;

}

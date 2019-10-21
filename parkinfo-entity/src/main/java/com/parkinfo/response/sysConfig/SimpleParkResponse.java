package com.parkinfo.response.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Li
 * description:
 * date: 2019-10-21
 */
@Data
public class SimpleParkResponse {

    @ApiModelProperty("园区id")
    private String id;

    @ApiModelProperty("园区名称")
    private String name;

}

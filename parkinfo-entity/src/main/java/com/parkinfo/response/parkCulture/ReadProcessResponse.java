package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 16:48
 **/
@Data
public class ReadProcessResponse {

    @ApiModelProperty(value = "进度id")
    private String id;

    @ApiModelProperty(value = "进度")
    private BigDecimal process;

    @ApiModelProperty(value = "是否必读")
    private Boolean necessary;
}

package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 17:22
 **/
@Data
public class SetReadProcessRequest {

    @ApiModelProperty(value = "进度id")
    @NotBlank(message = "进度id不能为空")
    private String id;

    @ApiModelProperty(value = "阅读进度")
    @NotNull(message = "阅读进度不能为空")
    private BigDecimal process;
}

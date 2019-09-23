package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-23 14:36
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SetBookRequest extends AddBookRequest{

    @ApiModelProperty(value = "图书id")
    @NotBlank(message = "图书id")
    private String id;
}

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
 * @create 2019-09-09 18:20
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SetVideoRequest extends AddVideoRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "视频id不能为空")
    private String id;
}

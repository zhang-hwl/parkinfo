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
 * @create 2019-09-12 10:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SetLiveBroadcastRequest extends AddLiveBroadcastRequest{

    @ApiModelProperty(value = "直播id")
    @NotBlank(message = "直播id不能为空")
    private String id;

}

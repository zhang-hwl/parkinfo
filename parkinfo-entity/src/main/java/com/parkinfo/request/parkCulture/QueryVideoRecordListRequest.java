package com.parkinfo.request.parkCulture;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-10 13:51
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVideoRecordListRequest extends PageRequest {

    @ApiModelProperty(value = "视频id")
    @NotBlank(message = "视频id不能为空")
    private String videoId;
}

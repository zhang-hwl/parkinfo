package com.parkinfo.request.parkCulture;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-09 16:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVideoCommentListRequest extends PageRequest {

    @ApiModelProperty(value = "视频id")
    @NotBlank(message = "视频id不能为空")
    private String videoId;
}

package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-09 16:20
 **/
@Data
public class AddVideoCommentRequest {

    @ApiModelProperty(value = "视频id")
    @NotBlank(message = "视频id不能为空")
    private String videoId;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论不能为空")
    private String comment;

}

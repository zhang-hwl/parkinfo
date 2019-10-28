package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-09 17:20
 **/
@Data
public class AddVideoRequest {

    @ApiModelProperty(value = "视频封面")
    @NotBlank(message = "视频封面不能为空")
    private String cover;

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "视频分类不能为空")
    private String categoryId;

    @ApiModelProperty(value = "视频名称")
    @NotBlank(message = "视频名称不能为空")
    private String name;

    @ApiModelProperty(value = "视频原文件名称")
    @NotBlank(message = "视频原文件名称不能为空")
    private String fileName;

    @ApiModelProperty(value = "videoId")
    @NotBlank(message = "videoId不能为空")
    private String videoId;

    @ApiModelProperty(value = "备注")
    private String remark;
}

package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String cover;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "videoId")
    private String videoId;

    @ApiModelProperty(value = "备注")
    private String remark;
}

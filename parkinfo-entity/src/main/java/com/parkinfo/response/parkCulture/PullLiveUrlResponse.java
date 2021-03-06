package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 10:53
 **/
@Data
@Builder
public class PullLiveUrlResponse {

    @ApiModelProperty(value = "流畅")
    private String lldUrl;

    @ApiModelProperty(value = "标清")
    private String lsdUrl;

    @ApiModelProperty(value = "高清")
    private String lhdUrl;

    @ApiModelProperty(value = "超清")
    private String ludUrl;
}

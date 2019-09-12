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
public class PushLiveUrlResponse {

    @ApiModelProperty(value = "推流服务器")
    private String url;

    @ApiModelProperty(value = "串流密钥")
    private String authKey;

}

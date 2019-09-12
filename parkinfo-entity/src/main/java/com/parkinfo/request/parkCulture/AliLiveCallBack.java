package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阿里云直播回调
 *
 * @author cnyuchu@gmail.com
 * @create 2019-07-03 13:59
 **/
@Data
public class AliLiveCallBack {

    private String action;

    private String app;

    private String appname;

    private String id;

    private String ip;

    private String node;

}

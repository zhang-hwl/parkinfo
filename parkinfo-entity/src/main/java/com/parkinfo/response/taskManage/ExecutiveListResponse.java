package com.parkinfo.response.taskManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-14 15:41
 **/
@Data
public class ExecutiveListResponse {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "所属园区id")
    private String parkId;
}

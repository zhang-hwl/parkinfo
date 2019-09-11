package com.parkinfo.request.compayManage;

import com.parkinfo.enums.DiscussStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SetDiscussRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "对接方式")
    private String connectWay;

    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "洽谈内容")
    private String content;

    @ApiModelProperty(value = "洽谈内容备注")
    private String remarkTalk;
}

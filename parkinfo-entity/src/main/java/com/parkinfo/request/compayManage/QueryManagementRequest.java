package com.parkinfo.request.compayManage;

import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryManagementRequest extends PageRequest {

    @ApiModelProperty(value = "园区id")
    private String parkId;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "对接方式")
    private String connectWay;

    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "类型id")
    private String typeId;

}

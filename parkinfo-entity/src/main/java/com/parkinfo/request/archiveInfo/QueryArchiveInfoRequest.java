package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryArchiveInfoRequest extends PageRequest {

    @ApiModelProperty(value = "文件大类")
    private String general;

    @ApiModelProperty(value = "文件种类")
    private String kind;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "园区ID")
    private String parkId;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty(value = "是否对外")
    private Boolean external;


}

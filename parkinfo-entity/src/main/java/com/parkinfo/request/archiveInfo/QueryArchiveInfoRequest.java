package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
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

    @ApiModelProperty(value = "本园区员工是否有查看权限")
    private Boolean parkPerson;

    @ApiModelProperty(value = "其他园区员工是否有查看权限")
    private Boolean otherParkPerson;

    @ApiModelProperty(value = "政府官员是否有查看权限")
    private Boolean government;

    @ApiModelProperty(value = "HR机构是否有查询权限")
    private Boolean hrOrgan;



}

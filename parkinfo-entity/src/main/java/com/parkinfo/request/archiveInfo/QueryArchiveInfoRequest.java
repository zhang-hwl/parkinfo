package com.parkinfo.request.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "PDF文件地址")
    private String pdfAddress;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "园区ID")
    private String parkId;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty(value = "是否对外")
    private Boolean external;

}

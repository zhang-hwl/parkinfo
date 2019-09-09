package com.parkinfo.response.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(value = "ArchiveInfoResponse", description = "存档资料返回数据")
public class ArchiveInfoResponse {

    @ApiModelProperty(value = "存档资料Id")
    private String id;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String fileAddress;

    @ApiModelProperty(value = "上传人")
    private String heir;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty(value = "大类")
    private String general;

    @ApiModelProperty(value = "种类")
    private String kind;

}

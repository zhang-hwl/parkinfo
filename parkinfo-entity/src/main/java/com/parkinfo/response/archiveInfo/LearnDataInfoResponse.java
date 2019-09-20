package com.parkinfo.response.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@ApiModel(value = "ArchiveInfoResponse", description = "存档资料中学习资料返回数据")
public class LearnDataInfoResponse {

    @ApiModelProperty(value = "存档资料Id")
    private String id;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty("书籍分类")
    private ArchiveInfoType archiveInfoType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String fileAddress;

    @ApiModelProperty(value = "PDF文件地址")
    private String pdfAddress;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

}

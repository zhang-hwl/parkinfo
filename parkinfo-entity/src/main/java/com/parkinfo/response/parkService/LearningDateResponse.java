package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.learningData.LearnDataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class LearningDateResponse {
    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("书籍地址")
    private String filePath;

    @ApiModelProperty("书籍简介")
    private String description;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("书籍分类")
    private LearnDataType learnDataType;
}

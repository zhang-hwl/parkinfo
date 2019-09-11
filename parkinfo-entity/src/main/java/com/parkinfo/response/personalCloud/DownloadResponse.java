package com.parkinfo.response.personalCloud;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DownloadResponse {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    private String fileUrl;
}

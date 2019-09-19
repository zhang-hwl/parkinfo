package com.parkinfo.request.compayManage;

import com.parkinfo.enums.EnclosureType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddFileRequest {

    @ApiModelProperty(value = "附件类型")
    private EnclosureType enclosureType;

    @ApiModelProperty(value = "附件名称")
    private String fileName;

    @ApiModelProperty(value = "附件url")
    private String fileUrl;
}

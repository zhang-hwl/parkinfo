package com.parkinfo.response.companyManage;

import com.parkinfo.enums.EnclosureType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EnclosureTotalResponse {

    @ApiModelProperty(value = "附件id")
    private String enclosureId;

    @ApiModelProperty(value = "附件类型")
    private EnclosureType enclosureType;

    @ApiModelProperty(value = "附件url")
    private String fileUrl;
}

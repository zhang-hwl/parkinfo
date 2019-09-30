package com.parkinfo.request.personalCloud;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPersonalCloudRequest extends PageRequest {
    @ApiModelProperty("文件名")
    private String fileName;
}

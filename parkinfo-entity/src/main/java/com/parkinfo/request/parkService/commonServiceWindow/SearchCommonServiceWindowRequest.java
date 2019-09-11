package com.parkinfo.request.parkService.commonServiceWindow;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCommonServiceWindowRequest extends PageRequest {
    @ApiModelProperty("服务")
    private String serverName;

    @ApiModelProperty("大类 id")
    private String bigTypeId;

    @ApiModelProperty("小类 id")
    private String smallTypeId;
}

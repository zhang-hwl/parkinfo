package com.parkinfo.request.parkService.businessAmuse;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchBusinessAmuseRequest extends PageRequest {
    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("大类 id")
    private String bigTypeId;

    @ApiModelProperty("小类 id")
    private String smallTypeId;
}

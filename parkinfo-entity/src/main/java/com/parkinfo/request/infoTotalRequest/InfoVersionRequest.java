package com.parkinfo.request.infoTotalRequest;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoVersionRequest extends PageRequest {

    @ApiModelProperty(value = "type")
    private String type;

}

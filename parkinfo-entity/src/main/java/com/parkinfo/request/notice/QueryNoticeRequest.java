package com.parkinfo.request.notice;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryNoticeRequest extends PageRequest {

    @ApiModelProperty("标题")
    private String title;

}

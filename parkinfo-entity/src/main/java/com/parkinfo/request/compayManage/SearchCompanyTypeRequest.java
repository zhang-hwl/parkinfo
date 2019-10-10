package com.parkinfo.request.compayManage;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:49
 */
@Data
public class SearchCompanyTypeRequest extends PageRequest {

    @ApiModelProperty("名称")
    private String name;

}

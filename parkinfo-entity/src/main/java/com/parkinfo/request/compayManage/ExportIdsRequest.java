package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Li
 * description:
 * date: 2019-10-18
 */
@Data
public class ExportIdsRequest {

    @ApiModelProperty("ids")
    private List<String> ids;

}

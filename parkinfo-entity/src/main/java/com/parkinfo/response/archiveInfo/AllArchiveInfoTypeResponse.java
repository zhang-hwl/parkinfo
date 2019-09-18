package com.parkinfo.response.archiveInfo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllArchiveInfoTypeResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String type;

    @ApiModelProperty(value = "类型")
    List<ArchiveInfoTypeResponse> kind;

}

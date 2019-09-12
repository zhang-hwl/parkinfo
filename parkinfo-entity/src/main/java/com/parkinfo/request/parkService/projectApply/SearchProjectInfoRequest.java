package com.parkinfo.request.parkService.projectApply;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SearchProjectInfoRequest extends PageRequest {
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("分类id")
    private String typeId;
}

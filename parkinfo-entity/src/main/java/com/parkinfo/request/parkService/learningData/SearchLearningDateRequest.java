package com.parkinfo.request.parkService.learningData;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchLearningDateRequest extends PageRequest {
    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("大类 id")
    private String bigTypeId;

    @ApiModelProperty("小类 id")
    private String smallTypeId;
}

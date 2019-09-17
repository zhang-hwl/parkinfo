package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArchiveReadRecordRequest extends PageRequest {

    @NotBlank(message = "文件ID不能为空")
    @ApiModelProperty(value = "文件id")
    private String id;

}

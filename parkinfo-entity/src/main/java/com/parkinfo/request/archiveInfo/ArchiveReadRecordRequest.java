package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArchiveReadRecordRequest extends PageRequest {

    @ApiModelProperty(value = "文件id")
    private String id;

}

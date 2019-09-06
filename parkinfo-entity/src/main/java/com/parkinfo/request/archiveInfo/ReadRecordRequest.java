package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReadRecordRequest {

    @ApiModelProperty(value = "文件ID")
    private String archiveId;

    @ApiModelProperty(value = "评论")
    private String remark;

}

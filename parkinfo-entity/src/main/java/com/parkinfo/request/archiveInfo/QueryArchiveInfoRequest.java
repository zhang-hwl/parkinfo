package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryArchiveInfoRequest extends PageRequest {

    @ApiModelProperty(value = "具体文件类型")
    private String finalMenu;

    @ApiModelProperty(value = "二级文件类型")
    private String subMenu;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}

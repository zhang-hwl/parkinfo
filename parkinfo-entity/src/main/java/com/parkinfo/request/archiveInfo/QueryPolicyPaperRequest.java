package com.parkinfo.request.archiveInfo;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPolicyPaperRequest extends PageRequest {

    @ApiModelProperty(value = "政策文件类型", notes = "前端根据页面添加")
    private String policyType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}

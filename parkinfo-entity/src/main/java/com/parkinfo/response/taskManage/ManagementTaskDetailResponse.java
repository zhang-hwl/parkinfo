package com.parkinfo.response.taskManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 11:11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ManagementTaskDetailResponse extends ManagementTaskListResponse{

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fileList;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 反馈
     */
    @ApiModelProperty(value = "反馈")
    private String feedBack;
}

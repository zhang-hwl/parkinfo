package com.parkinfo.request.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 14:48
 **/
@Data
public class WorkPlanDetailRequest {

    @ApiModelProperty(value = "任务详情id")
    private String id;

    /**
     * 工作内容
     */
    @ApiModelProperty(value = "工作内容")
    private String workContent;

    /**
     * 解决方案
     */
    @ApiModelProperty(value = "解决方案")
    private String solution;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String principals;

    /**
     * 支持人
     */
    @ApiModelProperty(value = "支持人")
    private String supporters;

    @ApiModelProperty(value = "完成情况")
    private Boolean finished;

    /**
     * 实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "实施时间")
    private Date implementationTime;
}

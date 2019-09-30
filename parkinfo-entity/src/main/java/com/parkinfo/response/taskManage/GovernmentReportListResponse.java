package com.parkinfo.response.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 13:39
 **/
@Data
public class GovernmentReportListResponse {

    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String name;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private String author;

    /**
     * 所属园区
     */
    @ApiModelProperty(value = "所属园区")
    private String parkName;


    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private List<ReceiverListResponse> receivers;

    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    private Integer step;

    /**
     * 是否完成
     */
    @ApiModelProperty(value = "是否完成")
    private Boolean finished;

    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}

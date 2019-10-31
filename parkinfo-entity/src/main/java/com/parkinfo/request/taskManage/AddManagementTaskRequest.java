package com.parkinfo.request.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 11:10
 **/
@Data
public class AddManagementTaskRequest {


    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    @Length(min = 0,max = 100,message = "任务名称不超过100个字")
    @NotNull(message = "任务名称不能为空")
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
     * 接收人
     */
    @ApiModelProperty(value = "接收人id")
    @NotNull(message = "接收人id不能为空")
    private List<String> receiverIds;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fileList;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @Length(min = 0,max = 255,message = "内容不超过255个字")
    private String content;
}

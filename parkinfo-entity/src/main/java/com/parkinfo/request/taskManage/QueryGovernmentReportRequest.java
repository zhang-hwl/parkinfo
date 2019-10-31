package com.parkinfo.request.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.TaskType;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 13:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryGovernmentReportRequest extends PageRequest {
    @ApiModelProperty(value = "提交时间起")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeFrom;

    @ApiModelProperty(value = "提交时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeTo;

    @ApiModelProperty(value = "园区id")
    private String parkId;

    @ApiModelProperty(value = "任务名称")
    @Length(min = 0,max = 100,message = "任务名称不超过100个字")
    @NotNull(message = "任务名称不能为空")
    private String name;

    @ApiModelProperty(value = "发起或接受")
    @NotNull(message = "类型必传")
    private TaskType taskType;
}

package com.parkinfo.request.taskManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-10 17:11
 **/
@Data
public class ExportWorkPlanRequest {

    @ApiModelProperty(value = "任务id")
    private List<String> ids;
}

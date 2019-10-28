package com.parkinfo.request.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.BroadcastType;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 10:22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryLiveListRequest extends PageRequest {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "直播状态")
    private BroadcastType live;

    @ApiModelProperty(value = "直播开始日期 - 起",example = "2019-06-28")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveStartTimeFrom;

    @ApiModelProperty(value = "直播开始日期 - 止",example = "2019-06-28")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveStartTimeTo;

}

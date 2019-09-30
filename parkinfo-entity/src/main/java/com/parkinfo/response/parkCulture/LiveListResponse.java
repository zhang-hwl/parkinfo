package com.parkinfo.response.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.BroadcastType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 10:26
 **/
@Data
public class LiveListResponse {

    @ApiModelProperty(value = "直播id")
    private String id;

    @ApiModelProperty(value = "直播封面")
    private String banner;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "直播人")
    private String anchor;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveStartTime;

    @ApiModelProperty(value = "直播状态")
    @Enumerated(EnumType.ORDINAL)//字段持久化为0，1
    private BroadcastType live;
}

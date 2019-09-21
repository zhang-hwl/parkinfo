package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.hpsf.Date;

import java.util.List;

@Data
public class MeetingRoomResponse {
    @ApiModelProperty("当天预约记录")
    private List<MeetingRoomReserveResponse> reserveResponse;

    @ApiModelProperty("会议室id")
    private String id;

    @ApiModelProperty(value = "会议室名称")
    private String roomName;

    @ApiModelProperty(value = "容纳人数")
    private Integer capacity;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人名称")
    private String userName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
}

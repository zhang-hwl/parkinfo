package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 16:36
 **/
@Data
public class AnswerSheetListResponse {

    @ApiModelProperty(value = "试卷id")
    private String id;

    @ApiModelProperty(value = "试卷名称")
    private String name;

    @ApiModelProperty(value = "考生昵称")
    private String nickname;

    @ApiModelProperty(value = "是否开始")
    private Boolean start;

    @ApiModelProperty(value = "答题时间（分钟）")
    private Integer time;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "正确答案数量")
    private Integer correctNum;

    @ApiModelProperty(value = "错误答案数量")
    private Integer wrongNum;

}

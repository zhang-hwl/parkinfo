package com.parkinfo.response.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-26 16:02
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerSheetDetailResponse extends AnswerSheetListResponse {

    @ApiModelProperty(value = "单选试题")
    private List<QuestionDetailListResponse> radioQuestionList;

    @ApiModelProperty(value = "判断试题")
    private List<QuestionDetailListResponse> judgeQuestionList;

    @ApiModelProperty(value = "当前服务端时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date currentTime;
}

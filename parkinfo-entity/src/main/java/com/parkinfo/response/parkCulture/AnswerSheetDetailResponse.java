package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class AnswerSheetDetailResponse extends AnswerSheetListResponse{

    @ApiModelProperty(value = "单选试题")
    private List<QuestionDetailListResponse> radioQuestionList;

    @ApiModelProperty(value = "判断试题")
    private List<QuestionDetailListResponse> judgeQuestionList;
}

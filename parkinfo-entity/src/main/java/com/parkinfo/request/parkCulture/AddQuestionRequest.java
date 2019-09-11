package com.parkinfo.request.parkCulture;

import com.parkinfo.enums.AnswerType;
import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 14:08
 **/
@Data
public class AddQuestionRequest {


    @ApiModelProperty(value = "试题分类id")
    @NotBlank(message = "试题分类id不能为空")
    private String categoryId;

    @ApiModelProperty(value = "问题")
    @NotBlank(message = "问题不能为空")
    private String question;

    @ApiModelProperty(value = "问题类型(选择，判断)")
    @NotNull(message = "问题类型不能为空")
    private QuestionType questionType;

    /**
     * A选项
     */
    @ApiModelProperty(value = "A选项")
    private String optionA;

    /**
     * B选项
     */
    @ApiModelProperty(value = "B选项")
    private String optionB;

    /**
     * C选项
     */
    @ApiModelProperty(value = "C选项")
    private String optionC;

    /**
     * D选项
     */
    @ApiModelProperty(value = "D选项")
    private String optionD;

    /**
     * T选项
     */
    @ApiModelProperty(value = "T选项")
    private String optionT;

    /**
     * F选项
     */
    @ApiModelProperty(value = "F选项")
    private String optionF;

    /**
     * 正确答案
     */
    @ApiModelProperty(value = "正确答案")
    @NotNull(message = "正确答案不能为空")
    private AnswerType answer;

}

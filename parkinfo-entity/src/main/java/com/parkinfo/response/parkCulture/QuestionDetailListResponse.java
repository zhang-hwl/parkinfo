package com.parkinfo.response.parkCulture;

import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-26 16:04
 **/
@Data
public class QuestionDetailListResponse {

    @ApiModelProperty(value = "试题id")
    private String id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "问题类型(选择，判断)")
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

}

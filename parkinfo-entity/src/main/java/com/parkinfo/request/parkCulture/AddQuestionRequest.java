package com.parkinfo.request.parkCulture;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.parkinfo.enums.AnswerType;
import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
@ExcelTarget("AddQuestionRequest")
public class AddQuestionRequest {


    @ApiModelProperty(value = "试题分类id")
    @NotBlank(message = "试题分类id不能为空")
    @ExcelIgnore
    private String categoryId;

    @ApiModelProperty(value = "问题")
    @NotBlank(message = "问题不能为空")
    @Excel(name = "问题")
    @Length(min = 0,max = 100,message = "问题不能超过100个字符")
    private String question;

    @ApiModelProperty(value = "问题类型(选择，判断)")
    @NotNull(message = "问题类型不能为空")
    @Excel(name = "问题类型(Radio,JUDGE)")
    private QuestionType questionType;

    /**
     * A选项
     */
    @ApiModelProperty(value = "A选项")
    @Excel(name = "A选项")
    @Length(min = 0,max = 100,message = "A选项不能超过100个字符")
    private String optionA;

    /**
     * B选项
     */
    @ApiModelProperty(value = "B选项")
    @Excel(name = "B选项")
    @Length(min = 0,max = 100,message = "B选项不能超过100个字符")
    private String optionB;

    /**
     * C选项
     */
    @ApiModelProperty(value = "C选项")
    @Excel(name = "C选项")
    @Length(min = 0,max = 100,message = "C选项不能超过100个字符")
    private String optionC;

    /**
     * D选项
     */
    @ApiModelProperty(value = "D选项")
    @Excel(name = "D选项")
    @Length(min = 0,max = 100,message = "D选项不能超过100个字符")
    private String optionD;

    /**
     * T选项
     */
    @ApiModelProperty(value = "T选项")
    @Excel(name = "T选项")
    @Length(min = 0,max = 100,message = "T选项不能超过100个字符")
    private String optionT;

    /**
     * F选项
     */
    @ApiModelProperty(value = "F选项")
    @Excel(name = "F选项")
    @Length(min = 0,max = 100,message = "F选项不能超过100个字符")
    private String optionF;

    /**
     * 正确答案
     */
    @ApiModelProperty(value = "正确答案")
    @NotNull(message = "正确答案不能为空")
    @Excel(name = "正确答案(A,B,C,D,T,F)")
    private AnswerType answer;

}

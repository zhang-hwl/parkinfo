package com.parkinfo.request.parkCulture;

import com.parkinfo.enums.QuestionType;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 14:01
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryQuestionListRequest extends PageRequest {

    @ApiModelProperty(value = "问题分类id")
    private String categoryId;

    @ApiModelProperty(value = "题目类型")
    private QuestionType questionType;

    @ApiModelProperty(value = "试题")
    @Length(min = 0,max = 100,message = "试题不能超过100个字符")
    private String question;

}

package com.parkinfo.request.parkCulture;

import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 * 生成考卷的试题类型
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 15:03
 **/
@Data
public class GenerateQuestionRequest {

    @ApiModelProperty(value = "试题分类id")
    @NotBlank(message = "试题分类id不能为空")
    private String categoryId;

    @ApiModelProperty(value = "试题类型")
    @NotNull(message = "试题类型不能为空")
    private QuestionType questionType;

    @ApiModelProperty(value = "试题数量")
    @Min(value = 0,message = "试题数量不能小于0")
    private Integer count;
}

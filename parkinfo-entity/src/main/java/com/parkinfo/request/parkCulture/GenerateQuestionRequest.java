package com.parkinfo.request.parkCulture;

import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String categoryId;

    @ApiModelProperty(value = "试题类型")
    private QuestionType questionType;

    @ApiModelProperty(value = "试题数量")
    private Integer count;
}

package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-26 09:41
 **/
@Data
public class AddQuestionCategoryRequest {

    @ApiModelProperty(value = "分类名")
    @NotBlank(message = "分类名不能为空")
    @Length(min = 0,max = 100,message = "分类名不能超过100个字符")
    private String name;

    @ApiModelProperty(value = "分类描述")
    @Length(min = 0,max = 255,message = "分类描述不能超过255个字符")
    private String intro;
}

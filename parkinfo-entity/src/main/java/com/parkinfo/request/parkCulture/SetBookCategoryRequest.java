package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 14:28
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SetBookCategoryRequest extends AddBookCategoryRequest{

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "分类id不能为空")
    private String id;
}

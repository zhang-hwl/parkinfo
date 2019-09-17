package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 14:27
 **/
@Data
public class AddBookCategoryRequest {

    @ApiModelProperty(value = "父级分类id")
    private String parentId;

    @ApiModelProperty(value = "分类名")
    @NotBlank(message = "分类名不能为空")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String intro;
}

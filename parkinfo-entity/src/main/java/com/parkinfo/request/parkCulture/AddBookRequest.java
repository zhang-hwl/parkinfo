package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-23 14:36
 **/
@Data
public class AddBookRequest {

    /**
     * 书名
     */
    @ApiModelProperty(value = "书名")
    @NotBlank(message = "书名不能为空")
    private String name;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
    @NotBlank(message = "封面不能为空")
    private String cover;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    @NotBlank(message = "作者不能为空")
    private String author;

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "分类id不能为空")
    private String categoryId;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String source;
}

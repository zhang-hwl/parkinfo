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
 * @create 2019-09-23 14:36
 **/
@Data
public class AddBookRequest {

    /**
     * 书名
     */
    @ApiModelProperty(value = "书名")
    @NotBlank(message = "书名不能为空")
    @Length(min = 0,max = 100,message = "评论不能超过100个字符")
    private String name;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
//    @NotBlank(message = "封面不能为空")
    private String cover;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    @NotBlank(message = "作者不能为空")
    @Length(min = 0,max = 100,message = "评论不能超过100个字符")
    private String author;

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "分类id不能为空")
    private String categoryId;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    @Length(min = 0,max = 255,message = "评论不能超过255个字符")
    private String summary;

    @ApiModelProperty(value = "文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String source;

    @ApiModelProperty(value = "文件名称")
    private String fileName;
}

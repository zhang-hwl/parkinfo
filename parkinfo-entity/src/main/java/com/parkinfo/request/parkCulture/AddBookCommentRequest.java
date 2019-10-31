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
 * @create 2019-09-05 16:27
 **/
@Data
public class AddBookCommentRequest {

    @ApiModelProperty(value = "图书id")
    @NotBlank(message = "图书id不能为空")
    private String bookId;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论不能为空")
    @Length(min = 0,max = 255,message = "评论不能超过255个字符")
    private String comment;
}

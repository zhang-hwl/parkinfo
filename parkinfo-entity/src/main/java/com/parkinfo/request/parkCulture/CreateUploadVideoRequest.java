package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-07-25 14:52
 **/
@Data
public class CreateUploadVideoRequest {

    @ApiModelProperty(value = "文件标题")
    @NotBlank(message = "文件标题不能为空")
    private String title;

    @ApiModelProperty(value = "源文件名称")
    @NotBlank(message = "源文件名不能为空")
    private String fileName;
}

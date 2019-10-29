package com.parkinfo.request.parkService.learningData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddLearningDataRequest {
    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("书籍地址")
    @NotBlank(message = "文件不能为空")
    private String filePath;

    @ApiModelProperty("书籍简介")
    private String description;

    @ApiModelProperty("图书分类id 小类")
    @NotBlank(message = "图书分类不能为空")
    private String typeId;
}

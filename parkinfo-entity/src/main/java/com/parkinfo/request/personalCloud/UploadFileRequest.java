package com.parkinfo.request.personalCloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class UploadFileRequest {

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("文件")
    private MultipartFile multipartFile;

    @ApiModelProperty("文件地址")
    @NotBlank(message = "请选上传文件")
    private String url;
}

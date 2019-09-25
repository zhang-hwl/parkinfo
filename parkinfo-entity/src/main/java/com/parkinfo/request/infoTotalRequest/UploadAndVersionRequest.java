package com.parkinfo.request.infoTotalRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadAndVersionRequest {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件")
    private MultipartFile multipartFile;

}

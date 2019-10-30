package com.parkinfo.request.parkService.commonServiceWindow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CommonServiceWindowTypeRequest {

    @ApiModelProperty("id")
    private String generalId;

    @ApiModelProperty("类型名称")
    @NotBlank(message = "类型名称不能为空")
    @Length(max = 9,message = "不能超过9个字符")
    private String generalName;

    @ApiModelProperty("小类id")
    private String kindId;

    @ApiModelProperty("小类名称")
    @Length(max = 9,message = "不能超过9个字符")
    private String kindName;

}

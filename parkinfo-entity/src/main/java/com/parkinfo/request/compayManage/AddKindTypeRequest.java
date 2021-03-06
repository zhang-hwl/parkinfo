package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:31
 */
//新增小类
@Data
public class AddKindTypeRequest {

    @ApiModelProperty("大类id")
    @NotBlank(message = "大类不能为空")
    private String id;

    @ApiModelProperty("新增小类名称")
    @NotBlank(message = "小类名称不能为空")
    @Length(max = 9,message = "不能超过9个字符")
    private String name;

}

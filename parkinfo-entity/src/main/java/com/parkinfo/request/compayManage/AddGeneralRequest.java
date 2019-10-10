package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:33
 */
@Data
public class AddGeneralRequest {

    @ApiModelProperty("新增大类名称")
    @NotBlank(message = "大类名称不能为空")
    private String name;

}

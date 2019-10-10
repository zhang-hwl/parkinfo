package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:36
 */
@Data
public class EditTypeRequest {

    @ApiModelProperty("类型id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty("类型名称")
    @NotBlank(message = "类型名称不能为空")
    private String name;

}

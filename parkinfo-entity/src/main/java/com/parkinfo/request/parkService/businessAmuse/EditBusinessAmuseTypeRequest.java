package com.parkinfo.request.parkService.businessAmuse;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditBusinessAmuseTypeRequest {

    @NotBlank(message = "类型不能为空")
    private String id;

    //类型
    @NotBlank(message = "类型名称不能为空")
    private String type;

}

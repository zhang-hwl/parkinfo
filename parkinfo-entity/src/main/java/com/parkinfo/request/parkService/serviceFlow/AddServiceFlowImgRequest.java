package com.parkinfo.request.parkService.serviceFlow;

import com.parkinfo.enums.ServiceFlowImgType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddServiceFlowImgRequest {
    @ApiModelProperty(value = "id 编辑时传递")
    private String id;

    @ApiModelProperty(value = "流程图类型",example = "DISCOUNT_POLICY,SETTLED_IN_APPLY,MEETING_RESERVE,COMPANY_REGISTER,LICENCE_HANDLE")
    @NotNull(message = "流程图类型不能为空")
    private ServiceFlowImgType imgType;

    @ApiModelProperty(value = "流程图地址")
    @NotBlank(message = "流程图地址不能为空")
    private String path;
}

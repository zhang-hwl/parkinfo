package com.parkinfo.request.parkService.serviceFlow;

import com.parkinfo.enums.ServiceFlowImgType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class SearchServiceFlowImgRequest {
    @ApiModelProperty(value = "流程图类型",example = "DISCOUNT_POLICY,SETTLED_IN_APPLY,MEETING_RESERVE,COMPANY_REGISTER,LICENCE_HANDLE")
    @NotNull(message = "流程图类型不能为空")
    private ServiceFlowImgType imgType;

}

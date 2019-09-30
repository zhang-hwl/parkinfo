package com.parkinfo.request.homePage;

import com.parkinfo.enums.TimeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReportRequest {

    @NotNull
    @ApiModelProperty("当季或者当年")
    private TimeEnum timeEnum;

}

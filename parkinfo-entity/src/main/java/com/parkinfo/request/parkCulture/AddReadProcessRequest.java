package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-25 14:35
 **/
@Data
public class AddReadProcessRequest {

    @ApiModelProperty(value = "图书id")
    @NotBlank(message = "图书id不能为空")
    private String bookId;

    @ApiModelProperty(value = "要分配的用户id")
    @NotNull(message = "用户id组不能为空")
    private List<String> userIds;

}

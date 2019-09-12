package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 14:36
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SetQuestionRequest extends AddQuestionRequest{

    @ApiModelProperty(value = "题目id")
    @NotBlank(message = "题目id不能为空")
    private String id;
}

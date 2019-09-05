package com.parkinfo.request.parkCulture;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 16:14
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryBookCommentListRequest extends PageRequest {

    @ApiModelProperty(value = "图书id")
    @NotBlank(message = "图书id不能为空")
    private String bookId;
}

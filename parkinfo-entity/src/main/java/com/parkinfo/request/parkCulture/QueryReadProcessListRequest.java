package com.parkinfo.request.parkCulture;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-25 11:17
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryReadProcessListRequest extends PageRequest {

    @ApiModelProperty(value = "图书id")
    private String bookId;

    @ApiModelProperty(value = "是否必读")
    private Boolean necessary;
}

package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-23 10:25
 **/
@Data
public class QueryCategoryListRequest {

    @ApiModelProperty(value = "上级分类id，不传为1级分类")
    private String parentId;
}

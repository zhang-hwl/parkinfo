package com.parkinfo.request.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-23 09:51
 **/
@Data
public class QueryVideoCategoryListRequest {

    @ApiModelProperty(value = "上级分类id，不传为顶级")
    private String parentId;
}

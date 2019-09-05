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
 * @create 2019-09-05 15:25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryBookListRequest extends PageRequest {

    @ApiModelProperty(value = "大类id")
    private String firstCategoryId;

    @ApiModelProperty(value = "种类id")
    private String secondCategoryId;

    @ApiModelProperty(value = "小类id")
    private String thirdCategoryId;

    @ApiModelProperty(value = "书籍名称")
    private String name;
}

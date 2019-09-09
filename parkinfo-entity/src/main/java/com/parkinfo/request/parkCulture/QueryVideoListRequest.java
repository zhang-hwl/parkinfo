package com.parkinfo.request.parkCulture;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-06 17:23
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVideoListRequest extends PageRequest {

    @ApiModelProperty(value = "大类id")
    private String firstCategoryId;

    @ApiModelProperty(value = "小类id")
    private String secondCategoryId;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "创建时间起")
    private Date createTimeFrom;

    @ApiModelProperty(value = "创建时间止")
    private Date createTimeTo;
}
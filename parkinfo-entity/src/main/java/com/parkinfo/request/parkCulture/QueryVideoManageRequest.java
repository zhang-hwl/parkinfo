package com.parkinfo.request.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @create 2019-09-09 17:57
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVideoManageRequest extends PageRequest {

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "创建时间起")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeFrom;

    @ApiModelProperty(value = "创建时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeTo;
}
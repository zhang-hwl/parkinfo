package com.parkinfo.request.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 0,max = 100,message = "视频名称不能超过100个字符")
    private String name;

    @ApiModelProperty(value = "创建时间起")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeFrom;

    @ApiModelProperty(value = "创建时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeTo;
}

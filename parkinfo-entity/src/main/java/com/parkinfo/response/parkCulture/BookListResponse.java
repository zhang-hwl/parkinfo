package com.parkinfo.response.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 15:30
 **/
@Data
public class BookListResponse {

    @ApiModelProperty(value = "图书id")
    private String id;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "名称")
    private String name;
}

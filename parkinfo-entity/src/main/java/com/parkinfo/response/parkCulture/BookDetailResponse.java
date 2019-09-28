package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 16:01
 **/
@Data
public class BookDetailResponse {

    @ApiModelProperty(value = "图书id")
    private String id;

    @ApiModelProperty(value = "书名")
    private String name;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "已看人数")
    private Integer readNum;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "地址")
    private String source;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "一级分类id")
    private String thirdCategoryId;

    @ApiModelProperty(value = "二级分类id")
    private String secondCategoryId;

    @ApiModelProperty(value = "三级分类id")
    private String firstCategoryId;
}

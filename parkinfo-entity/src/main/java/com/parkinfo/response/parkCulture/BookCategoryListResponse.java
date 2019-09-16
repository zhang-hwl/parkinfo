package com.parkinfo.response.parkCulture;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 14:22
 **/
@Data
public class BookCategoryListResponse {

    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String intro;

    @ApiModelProperty(value = "二级分类")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookCategoryListResponse> children = new ArrayList<>();
}

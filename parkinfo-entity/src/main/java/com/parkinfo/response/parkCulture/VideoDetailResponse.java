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
 * @create 2019-09-09 15:43
 **/
@Data
public class VideoDetailResponse {

    @ApiModelProperty(value = "视频id")
    private String id;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "观看量")
    private Integer readNum;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "视频id")
    private String videoId;

    @ApiModelProperty(value = "视频原文件名称")
    private String fileName;

    @ApiModelProperty(value = "一级分类id")
    private String firstCategoryId;

    @ApiModelProperty(value = "二级分类id")
    private String secondCategoryId;

    @ApiModelProperty(value = "备注")
    private String remark;
}

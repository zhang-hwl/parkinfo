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
 * @create 2019-09-06 17:25
 **/
@Data
public class VideoListResponse {

    @ApiModelProperty(value = "视频id")
    private String id;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "上传人姓名")
    private String uploader;

    @ApiModelProperty(value = "观看量")
    private Integer readNum;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

package com.parkinfo.response.parkCulture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-25 11:16
 **/
@Data
public class ReadProcessListResponse {

    private String id;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "书名")
    private String bookName;

    @ApiModelProperty(value = "阅读进度")
    private BigDecimal process;

    @ApiModelProperty(value = "分配日期")
    private Date createTime;
}

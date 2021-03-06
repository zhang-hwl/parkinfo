package com.parkinfo.request.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 10:37
 **/
@Data
public class AddLiveBroadcastRequest {

    @ApiModelProperty(value = "直播封面")
    @NotBlank(message = "直播封面不能为空")
    private String banner;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    @Length(min = 0,max = 100,message = "标题不能超过100个字符")
    private String title;

    @ApiModelProperty(value = "直播人")
    @NotBlank(message = "直播人不能为空")
    @Length(min = 0,max = 100,message = "直播人不能超过100个字符")
    private String anchor;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "直播开始时间不能为空")
    private Date liveStartTime;

    @ApiModelProperty(value = "详情")
    @Length(min = 0,max = 255,message = "详情不能超过255个字符")
    private String desc;
}

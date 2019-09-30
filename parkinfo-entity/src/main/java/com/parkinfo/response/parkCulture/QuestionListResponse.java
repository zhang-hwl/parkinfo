package com.parkinfo.response.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.QuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 14:03
 **/
@Data
public class QuestionListResponse {


    @ApiModelProperty(value = "题目id")
    private String id;
    /**
     * 题目名称
     */
    @ApiModelProperty(value = "题目名称")
    private String question;

    /**
     * 上传人
     */
    @ApiModelProperty(value = "上传人")
    private String uploader;


    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    /**
     * 题目类型
     */
    @ApiModelProperty(value = "题目类型")
    private QuestionType questionType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}

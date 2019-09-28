package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Data
public class FeedbackResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("运营方名称")
    private String operatorName;

    @ApiModelProperty("问题描述")
    private String questionDescription;

    @ApiModelProperty("图片地址")
    private String imgPath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}

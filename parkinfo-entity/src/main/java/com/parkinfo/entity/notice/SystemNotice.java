package com.parkinfo.entity.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class SystemNotice {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "封面图")
    @NotBlank(message = "封面图不能为空")
    private String cover;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "概要")
    @NotBlank(message = "概要不能为空")
    private String profile;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String text;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty("上传人姓名")
    private String name;

}

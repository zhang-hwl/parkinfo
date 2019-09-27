package com.parkinfo.entity.notice;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "c_system_notice")
@ApiModel(value = "SystemNoticeEntity", description = "公告")
public class SystemNoticeEntity extends BaseEntity {

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "概要")
    private String profile;

    @ApiModelProperty(value = "富文本")
    private String text;

}

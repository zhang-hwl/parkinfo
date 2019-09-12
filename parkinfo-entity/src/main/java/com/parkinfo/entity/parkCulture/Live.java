package com.parkinfo.entity.parkCulture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.enums.BroadcastType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 10:22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_live")
@Table(appliesTo = "c_live",comment = "直播表")
public class Live extends BaseEntity {

    @ApiModelProperty(value = "直播设置-appName")
    private String appName;

    @ApiModelProperty(value = "直播设置-streamName")
    private String streamName;

    @ApiModelProperty(value = "直播设置-推流域名")
    private String pushDomain;

    @ApiModelProperty(value = "直播设置-拉流域名")
    private String pullDomain;

    @ApiModelProperty(value = "直播设置-推流鉴权key")
    @Column(name = "`pushKey`")
    private String pushKey;

    @ApiModelProperty(value = "直播设置-拉流鉴权key")
    @Column(name = "`pullKey`")
    private String pullKey;

    @ApiModelProperty(value = "直播封面")
    private String banner;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveStartTime;

    @ApiModelProperty(value = "直播状态")
    @Enumerated(EnumType.ORDINAL)//字段持久化为0，1
    private BroadcastType live;

    @ApiModelProperty(value = "详情")
    @Column(name = "`desc`",columnDefinition = "text")
    private String desc;
}

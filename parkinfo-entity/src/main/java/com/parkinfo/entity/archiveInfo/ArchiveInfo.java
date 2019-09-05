package com.parkinfo.entity.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "c_archive_info", indexes = {@Index(name = "index_general", columnList = "general"), @Index(name = "index_kind", columnList = "kind")})
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ArchiveInfo", description = "存档资料")
public class ArchiveInfo extends BaseEntity {

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String fileAddress;

    @ApiModelProperty(value = "上传人")
    private String heir;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "园区名称")
    private String gradenName;

    @Column(name = "`external`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否对外")
    private Boolean external;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty(value = "大类")
    private String general;

    @ApiModelProperty(value = "种类")
    private String kind;

    //关联园区 todo

}

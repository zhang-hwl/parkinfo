package com.parkinfo.entity.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_archive_info", indexes = {@Index(columnList = "general_id")})
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

    @Column(name = "`external`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否对外")
    private Boolean external;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    //冗余字段，查询使用
    @Column(name = "general_id")
    private String generalId;

    @ApiModelProperty(value = "种类")
    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "kind_id")
    private ArchiveInfoType kind;

    @Column(name = "`parkPerson`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "本园区员工是否有查看权限")
    private Boolean parkPerson;

    @Column(name = "`otherParkPerson`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "其他园区员工是否有查看权限")
    private Boolean otherParkPerson;

    @Column(name = "`government`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "政府官员是否有查看权限")
    private Boolean government;

    @Column(name = "`hrOrgan`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "HR机构是否有查询权限")
    private Boolean hrOrgan;

    @ManyToOne
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;

    @OneToMany
    @JoinColumn(name = "archiveInfo_id")
    //评论
    private List<ArchiveComment> archiveComments;

    @OneToMany
    @JoinColumn(name = "archiveInfo_id")
    //阅读记录
    private List<ArchiveReadRecord> archiveReadRecords;
}

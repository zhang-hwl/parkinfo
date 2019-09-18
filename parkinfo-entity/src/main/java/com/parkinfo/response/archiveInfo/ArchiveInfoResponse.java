package com.parkinfo.response.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@ApiModel(value = "ArchiveInfoResponse", description = "存档资料返回数据")
public class ArchiveInfoResponse {

    @ApiModelProperty(value = "存档资料Id")
    private String id;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "种类")
    @JoinColumn(name = "kind_id")
    private String kindId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    private String fileAddress;

    @ApiModelProperty(value = "PDF文件地址")
    private String pdfAddress;

    @ApiModelProperty(value = "园区名称")
    private String parkName;

    @ApiModelProperty(value = "上传人")
    private String heir;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "文档说明")
    private String remark;

    @ApiModelProperty(value = "是否对外")
    private Boolean external;

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

}

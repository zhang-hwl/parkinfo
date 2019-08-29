package com.parkinfo.entity.archiveInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_declare_paper")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "DeclarePaper", description = "存档资料-申报材料")
public class DeclarePaper extends BaseEntity {

    @ApiModelProperty(value = "申报材料类型")
    private String declareType;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "组织架构")
    private String organ;

    @ApiModelProperty(value = "上传人")
    private String heir;

    @ApiModelProperty(value = "项目负责人")
    private String projectManager;

    @ApiModelProperty(value = "项目金额")
    private BigDecimal projectAmount;

    @ApiModelProperty(value = "企业名称")
    private String firmName;

    @ApiModelProperty(value = "法人代表")
    private String legal;

    @ApiModelProperty(value = "公司地址")
    private String firmAddress;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "文件地址")
    private String paperUrl;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @LastModifiedDate
    private Date updateTime;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "文件备注")
    private String remark;
}

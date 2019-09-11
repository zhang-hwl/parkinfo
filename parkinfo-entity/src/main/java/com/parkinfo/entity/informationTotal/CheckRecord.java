package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//点检记录
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_check_record")
@ApiModel(value = "CheckRecordRequest", description = "信息统计-点检记录表")
public class CheckRecord extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "分类", width = 15)
    @ApiModelProperty(value = "分类")
    private String classification;

    @Excel(name = "运营标准事项", width = 30)
    @ApiModelProperty(value = "运营标准事项")
    private String operating;

    @Excel(name = "点检情况", width = 15)
    @ApiModelProperty(value = "点检情况")
    private String checkStatus;

    @Excel(name = "建议事项", width = 15)
    @ApiModelProperty(value = "建议事项")
    private String suggest;

    @Excel(name = "点检人", width = 15)
    @ApiModelProperty(value = "点检人")
    private String checkPerson;

    @Excel(name = "点检时间", width = 15, format = "yyyy-MM-dd")
    @ApiModelProperty(value = "点检时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String checkDate;

    @ManyToOne
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;

}

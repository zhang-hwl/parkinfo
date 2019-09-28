package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

//园区大事件
@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo"})
@Table(name = "c_big_event")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@ApiModel(value = "BigEvent", description = "信息统计-园区大事件")
public class BigEvent extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @Excel(name = "类型", width = 15, fixedIndex = 0)
    @ApiModelProperty(value = "类型")
    private String type;

    @Excel(name = "名称", width = 15, fixedIndex = 1)
    @ApiModelProperty(value = "名称")
    private String name;

    @Excel(name = "年份", width = 15, fixedIndex = 2)
    @ApiModelProperty(value = "年份")
    private String year;

    @Excel(name = "月份", width = 15, fixedIndex = 3)
    @ApiModelProperty(value = "月份")
    private String month;

    @Excel(name = "内容", width = 40, fixedIndex = 4)
    @ApiModelProperty(value = "内容")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;

}

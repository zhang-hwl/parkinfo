package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

//本园区关键信息
@Data
@Entity
@Table(name = "c_graden_key_info")
@ApiModel(value = "GradenKeyInfo", description = "信息统计-园区关键信息")
public class GradenKeyInfo extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "关键维度", width = 15)
    @ApiModelProperty(value = "关键维度")
    private String keyDimensions;

    @Excel(name = "说明", width = 30)
    @ApiModelProperty(value = "说明")
    private String remark;

    //关联园区， todo

}

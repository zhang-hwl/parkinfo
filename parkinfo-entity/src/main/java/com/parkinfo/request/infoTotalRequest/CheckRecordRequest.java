package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckRecordRequest extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "分类")
    private String classification;

    @ApiModelProperty(value = "运营标准事项")
    private String operating;

    @ApiModelProperty(value = "点检情况")
    private String checkStatus;

    @ApiModelProperty(value = "建议事项")
    private String suggest;

    @ApiModelProperty(value = "点检人")
    private String checkPerson;

    @ApiModelProperty(value = "点检时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String checkDate;

}
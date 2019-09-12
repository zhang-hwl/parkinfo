package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PolicyTotalRequest extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "项目")
    private String project;

    @ApiModelProperty(value = "地方留存比例")
    //可以为字符串，例如有，无，待补充
    private String remainRatio;

    @ApiModelProperty(value = "入驻第一年")
    private String enterOne;

    @ApiModelProperty(value = "入驻第二年")
    private String enterTwo;

    @ApiModelProperty(value = "入驻第三年")
    private String enterThree;

    @ApiModelProperty(value = "入驻第四年")
    private String enterFour;

    @ApiModelProperty(value = "入驻第五年")
    private String enterFive;

    @ApiModelProperty(value = "说明")
    private String remark;

}
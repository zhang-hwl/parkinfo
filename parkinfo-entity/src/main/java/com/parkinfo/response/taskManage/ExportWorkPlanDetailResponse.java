package com.parkinfo.response.taskManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-10 17:15
 **/
@Data
@ExcelTarget("ExportWorkPlanDetailResponse")
public class ExportWorkPlanDetailResponse {

    @Excel(name = "任务名称",mergeVertical=true)
    private String name;
    /**
     * 工作内容
     */
    @ApiModelProperty(value = "工作内容")
    @Excel(name = "工作内容")
    private String workContent;

    /**
     * 解决方案
     */
    @ApiModelProperty(value = "解决方案")
    @Excel(name = "解决方案")
    private String solution;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @Excel(name = "负责人")
    private String principals;

    /**
     * 支持人
     */
    @ApiModelProperty(value = "支持人")
    @Excel(name = "支持人")
    private String supporters;

    /**
     * 完成情况
     */
    @ApiModelProperty(value = "完成情况")
    @Excel(name = "完成情况",replace={"是_true","否_false"})
    private Boolean finished;

    /**
     * 实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "实施时间",format="yyyy年MM月dd日 HH:mm")
    private Date implementationTime;
}

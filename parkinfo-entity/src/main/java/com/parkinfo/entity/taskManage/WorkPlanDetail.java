package com.parkinfo.entity.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 13:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_plan_detail")
@Table(appliesTo = "c_plan_detail",comment = "工作内容详情表")
public class WorkPlanDetail extends BaseEntity {

    /**
     * 工作内容
     */
    @ApiModelProperty(value = "工作内容")
    private String workContent;

    /**
     * 解决方案
     */
    @ApiModelProperty(value = "解决方案")
    private String solution;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String principals;

    /**
     * 支持人
     */
    @ApiModelProperty(value = "支持人")
    private String supporters;

    /**
     * 实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date implementationTime;

    /**
     * 关联园区工作计划
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_id")
    @JsonIgnore
    private ParkWorkPlan parkWorkPlan;

    /**
     * 关联个人工作计划
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id")
    @JsonIgnore
    private PersonalWorkPlan personalWorkPlan;
}

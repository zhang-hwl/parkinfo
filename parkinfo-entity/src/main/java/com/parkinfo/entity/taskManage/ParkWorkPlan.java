package com.parkinfo.entity.taskManage;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.PlanType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 13:44
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_park_plan")
@Table(appliesTo = "c_park_plan",comment = "园区工作计划及小节表")
public class ParkWorkPlan extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 提交人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private ParkUser author;

    /**
     * 园区管理员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private ParkUser parkManager;

    /**
     * 所属园区
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_id")
    private ParkInfo park;

    /**
     * 类型
     */
    @Enumerated(EnumType.ORDINAL)//字段持久化为0，1
    private PlanType planType;

    /**
     * 反馈
     */
    @Column(columnDefinition = "text")
    private String feedback;

    /**
     * 进度
     */
    private Integer step;

    /**
     * 是否完成
     */
    private Boolean finished;

    /**
     * 结果反馈
     */
    @Column(columnDefinition = "text")
    private String resultFeedback;
}

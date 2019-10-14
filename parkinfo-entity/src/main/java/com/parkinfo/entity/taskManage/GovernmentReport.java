package com.parkinfo.entity.taskManage;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 13:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_government_report")
@Table(appliesTo = "c_government_report",comment = "政府工作汇报任务表")
public class GovernmentReport extends BaseEntity {

    /**
     * 任务名称
     */
    private String name;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 提交人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private ParkUser author;

    /**
     * 所属园区
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_id")
    private ParkInfo park;

    /**
     * 附件
     */
    private String fileList;

    /**
     * 内容
     */
    private String content;


    /**
     * 接收人
     */
    @ManyToMany(targetEntity = ParkUser.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_government_task_user", joinColumns = {@JoinColumn(name = "task_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<ParkUser> receivers;

    /**
     * 进度
     */
    private Integer step;

    /**
     * 是否完成
     */
    private Boolean finished;

    /**
     * 完成人
     */
    @ManyToMany(targetEntity = ParkUser.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_government_task_executive", joinColumns = {@JoinColumn(name = "task_id")}, inverseJoinColumns = {@JoinColumn(name = "executive_id")})
    private List<ParkUser> executive;

}

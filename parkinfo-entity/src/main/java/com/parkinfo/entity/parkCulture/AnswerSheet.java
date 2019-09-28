package com.parkinfo.entity.parkCulture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 13:28
 **/
@EqualsAndHashCode(callSuper = true, exclude = {"examination", "parkUser"})
@Data
@Entity(name = "c_answer_sheet")
@Table(appliesTo = "c_answer_sheet", comment = "答卷表")
//@AttributeOverride(name = "answers", column = @Column(name = "answers"))
public class AnswerSheet extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ParkUser parkUser;

    /**
     * 是否开始
     */
    private Boolean start;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 正确答案数量
     */
    private Integer correctNum;

    /**
     * 错误答案数量
     */
    private Integer wrongNum;

    /**
     * 答案
     */
    private String answers;

    /**
     * 考官
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private ParkUser creator;

    @ApiModelProperty(value = "答题时间（分钟）")
    private Integer time;
//     @Transient
//    private Map<String, Object> answers = new HashMap<>();

//    @Transient
//    public Map<String, String> getAnswers() {
//        return answers;
//    }
//
//    @Transient
//    public void setAnswers(Map<String, String> answers) {
//        this.answers = answers;
//    }

}

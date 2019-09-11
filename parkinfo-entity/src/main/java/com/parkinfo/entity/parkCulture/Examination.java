package com.parkinfo.entity.parkCulture;

import com.parkinfo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 11:08
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_examination")
@Table(appliesTo = "c_examination",comment = "试题分类表")
public class Examination extends BaseEntity {

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 答题时间（分钟）
     */
    private Integer time;

    /**
     * 试题列表
     */
    @ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_examination_question", joinColumns = {@JoinColumn(name = "examination_id")}, inverseJoinColumns = {@JoinColumn(name = "question_id")})
    private Set<Question> questionList;


}

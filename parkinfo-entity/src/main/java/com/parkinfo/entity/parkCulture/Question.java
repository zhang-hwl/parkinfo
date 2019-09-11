package com.parkinfo.entity.parkCulture;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.AnswerType;
import com.parkinfo.enums.QuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 10:27
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_question")
@Table(appliesTo = "c_question",comment = "试题库表")
public class Question extends BaseEntity {

    /**
     * 题目分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private QuestionCategory category;

    /**
     * 上传人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private ParkUser uploader;
    /**
     * 题目类型
     */
    @Enumerated(EnumType.ORDINAL)
    private QuestionType questionType;

    /**
     * 问题
     */
    private String question;

    /**
     * A选项
     */
    private String optionA;

    /**
     * B选项
     */
    private String optionB;

    /**
     * C选项
     */
    private String optionC;

    /**
     * D选项
     */
    private String optionD;

    /**
     * T选项
     */
    private String optionT;

    /**
     * F选项
     */
    private String optionF;

    /**
     * 正确答案
     */
    @Enumerated(EnumType.ORDINAL)
    private AnswerType answer;
}

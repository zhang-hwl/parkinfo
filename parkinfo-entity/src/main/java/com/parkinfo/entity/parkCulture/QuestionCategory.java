package com.parkinfo.entity.parkCulture;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 11:25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_question_category")
@Table(appliesTo = "c_question_category",comment = "试题分类表")
public class QuestionCategory extends BaseEntity {

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String intro;

}

package com.parkinfo.entity.parkCulture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 14:54
 **/
@EqualsAndHashCode(callSuper = true,exclude = {"parent","children"})
@Data
@Entity(name = "c_book_category")
@Table(appliesTo = "c_book_category",comment = "图书分类表")
public class BookCategory extends BaseEntity {

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String intro;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @JsonIgnoreProperties("children")
    private BookCategory parent;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    private Set<BookCategory> children = new HashSet<>(0);
}

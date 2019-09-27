package com.parkinfo.entity.parkCulture;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * 图书馆书籍
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 14:32
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_park_book")
@Table(appliesTo = "c_park_book",comment = "园区图书表")
public class Book extends BaseEntity {

    /**
     * 书名
     */
    private String name;

    /**
     * 封面
     */
    private String cover;

    /**
     * 作者
     */
    private String author;

    /**
     * 图书分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BookCategory category;

    /**
     * 简介
     */
    private String summary;

    /**
     * 上传人
     */
    private String uploader;

    /**
     * 文件路径
     */
    private String source;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 已看人数
     */
    private Integer readNum;
}

package com.parkinfo.entity.parkCulture;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkUser;
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
 * 图书评论
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 15:14
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_book_comment")
@Table(appliesTo = "c_book_comment",comment = "图书评论表")
public class BookComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private ParkUser reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * 评论
     */
    private String comment;
}

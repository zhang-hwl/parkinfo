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
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 11:29
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_live_comment")
@Table(appliesTo = "c_live_comment",comment = "直播评论表")
public class LiveComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private ParkUser reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "live_id")
    private Live live;

    /**
     * 评论
     */
    private String comment;
}
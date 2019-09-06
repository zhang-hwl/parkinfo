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
import java.math.BigDecimal;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 * 图书阅读进度
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 15:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_read_process")
@Table(appliesTo = "c_read_process",comment = "图书阅读进度表")
public class ReadProcess extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private ParkUser reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * 进度
     */
    private BigDecimal process;

    /**
     * 必读
     */
    private Boolean necessary;
}

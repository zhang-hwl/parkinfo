package com.parkinfo.entity.parkCulture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @create 2019-09-06 13:53
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_park_video")
@Table(appliesTo = "c_park_video",comment = "园区视频表")
public class Video extends BaseEntity {

    /**
     * 书名
     */
    private String name;

    /**
     * 封面
     */
    private String cover;

    /**
     * 上传人
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="uploader_id")
    private ParkUser uploader;

    /**
     * 观看量
     */
    private Integer readNum;

    /**
     * 阿里云视频id
     */
    private String videoId;

    /**
     * 备注
     */
    private String remark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private VideoCategory category;
}

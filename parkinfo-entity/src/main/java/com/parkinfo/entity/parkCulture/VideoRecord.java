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
 * @create 2019-09-10 13:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_video_record")
@Table(appliesTo = "c_video_record",comment = "视频观看记录表")
public class VideoRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watcher_id")
    private ParkUser watcher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}

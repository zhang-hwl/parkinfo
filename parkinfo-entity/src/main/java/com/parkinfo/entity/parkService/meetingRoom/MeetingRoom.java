package com.parkinfo.entity.parkService.meetingRoom;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_ser_meeting_room")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "MeetingRoom", description = "会议室")
public class MeetingRoom extends BaseEntity {
    @ApiModelProperty(value = "会议室名称")
    private String roomName;

    @ApiModelProperty(value = "容纳人数")
    private Integer capacity;

    @ApiModelProperty(value = "图片")
    private String picture;


}

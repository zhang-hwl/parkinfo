package com.parkinfo.entity.parkService.meetingRoom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true,exclude = {"reserveUser"})
@Data
@Entity
@Table(name = "c_ser_meeting_room_reserve")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "MeetingRoomReserve", description = "会议室预定")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class MeetingRoomReserve extends BaseEntity {

    @ApiModelProperty(value = "预定开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "预定结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ManyToOne()
    @JoinColumn(name = "meeting_room_id")
    @ApiModelProperty("会议室")
    private MeetingRoom meetingRoom;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @ApiModelProperty("预约人")
    @JsonIgnoreProperties("meetingRoomReserves")
    private ParkUser reserveUser;

    @ApiModelProperty("会议室预约时间区间")
    private String meetingRoomSections;

}

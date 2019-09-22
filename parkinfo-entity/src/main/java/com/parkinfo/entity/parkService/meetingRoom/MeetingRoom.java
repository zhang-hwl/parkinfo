package com.parkinfo.entity.parkService.meetingRoom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo", "parkUser", "meetingRoomReserves"})
@Data
@Entity
@Table(name = "c_ser_meeting_room")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "MeetingRoom", description = "会议室")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class MeetingRoom extends BaseEntity {
    @ApiModelProperty(value = "会议室名称")
    private String roomName;

    @ApiModelProperty(value = "容纳人数")
    private Integer capacity;

    @ApiModelProperty(value = "图片")
    @Column(columnDefinition = "text")
    private String picture;

    @ApiModelProperty(value = "备注")
    @Column(columnDefinition = "text")
    private String remark;

    @ManyToOne()
    @JoinColumn(name = "park_id")
    @JsonIgnoreProperties("parkInfo")
    private ParkInfo parkInfo;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("user_id")
    private ParkUser parkUser;

    @OneToMany(mappedBy = "meetingRoom")
    @JsonIgnoreProperties("meetingRoom")
    private List<MeetingRoomReserve> meetingRoomReserves = new ArrayList<>();
}

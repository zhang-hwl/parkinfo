package com.parkinfo.entity.userConfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoomReserve;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 09:30
 **/
@EqualsAndHashCode(callSuper = true,exclude = {"roles","parks","meetingRooms","meetingRoomReserves"})
@Data
@Entity(name = "c_park_user")
@org.hibernate.annotations.Table(appliesTo = "c_park_user",comment = "园区用户表")
public class ParkUser extends BaseEntity {

    /**
     * 账户
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 密码
     */
    private String password;

    @ManyToMany(targetEntity = ParkRole.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<ParkRole> roles = new HashSet<>();

    @ManyToMany(targetEntity = ParkInfo.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_user_park", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "park_id")})
    private Set<ParkInfo> parks = new HashSet<>();

    @OneToMany(mappedBy = "parkUser")
    @JsonIgnoreProperties("parkUser")
    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    @OneToMany(mappedBy = "reserveUser")
    @JsonIgnoreProperties("reserveUser")
    @ApiModelProperty("预约的会议室")
    private List<MeetingRoomReserve> meetingRoomReserves = new ArrayList<>();

}

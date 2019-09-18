package com.parkinfo.entity.userConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.parkService.meetingRoom.MeetingRoom;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

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
 * @create 2019-09-05 09:59
 **/
@EqualsAndHashCode(callSuper = true,exclude = {"manager","users","meetingRooms","companyDetails"})
@Data
@Entity(name = "c_park_info")
@Table(appliesTo = "c_park_info",comment = "园区信息表")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ParkInfo extends BaseEntity {

    /**
     * 园区名称
     */
    private String name;

    /**
     * 园区负责人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private ParkUser manager;

    @ManyToMany(mappedBy = "parks")
    @JsonIgnore
    private Set<ParkUser> users = new HashSet<>();

    @OneToMany(mappedBy = "parkInfo")
    @JsonIgnore
    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    @OneToMany(mappedBy = "parkInfo")
    @JsonIgnore
    private List<CompanyDetail> companyDetails = new ArrayList<>();

}

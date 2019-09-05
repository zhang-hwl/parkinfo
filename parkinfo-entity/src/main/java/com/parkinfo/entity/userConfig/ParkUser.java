package com.parkinfo.entity.userConfig;

import com.parkinfo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 09:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_park_user")
@org.hibernate.annotations.Table(appliesTo = "c_park_user",comment = "园区用户表")
public class ParkUser extends BaseEntity {

    /**
     * 账户
     */
    private String account;

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
}

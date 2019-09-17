package com.parkinfo.entity.userConfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 09:48
 **/
@EqualsAndHashCode(callSuper = true,exclude = "permissions")
@Data
@Entity(name = "c_park_role")
@Table(appliesTo = "c_park_role",comment = "园区权限表")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ParkRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String remark;

    @ManyToMany(targetEntity = ParkPermission.class, fetch = FetchType.LAZY)
    @JoinTable(name = "c_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    @JsonIgnoreProperties(value = "permissions")
    private Set<ParkPermission> permissions = new HashSet<>();

}

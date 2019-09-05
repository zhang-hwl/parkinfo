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
 * @create 2019-09-05 09:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "c_park_permission")
@Table(appliesTo = "c_park_permission",comment = "园区权限表")
public class ParkPermission extends BaseEntity {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String remark;

    /**
     * 上级权限
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private ParkPermission parent;

    /**
     * 下级权限
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    private Set<ParkPermission> children = new HashSet<>(0);

}

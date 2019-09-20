package com.parkinfo.entity.parkService.businessAmuse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true,exclude = {"parent","children","parkInfo"})
@Data
@Entity
@Table(name = "c_business_amuse_type")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "BusinessAmuseType",description =  "商务与周边娱乐类型")
public class BusinessAmuseType extends BaseEntity {
    //类型
    private String type;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    //上一级分类类型
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private BusinessAmuseType parent;

    //下级分类类型
    @OneToMany(cascade= CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    private Set<BusinessAmuseType> children = new HashSet<>(0);
}

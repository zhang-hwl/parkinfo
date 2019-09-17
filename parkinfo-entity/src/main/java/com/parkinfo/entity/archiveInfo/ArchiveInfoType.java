package com.parkinfo.entity.archiveInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true,exclude = {"parent","children"})
@Entity(name = "c_archive_info_type")
@Table(appliesTo = "c_archive_info_type",comment = "存档资料类型表")
public class ArchiveInfoType extends BaseEntity {

    //类型
    private String type;

    //上一级分类类型
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private ArchiveInfoType parent;

    //下级分类类型
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    private Set<ArchiveInfoType> children = new HashSet<>(0);

}

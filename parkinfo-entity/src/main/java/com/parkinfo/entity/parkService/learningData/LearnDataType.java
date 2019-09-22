package com.parkinfo.entity.parkService.learningData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true,exclude = {"parent","children"})
@Entity(name = "c_learn_data_type")
@Table(appliesTo = "c_learn_data_type",comment = "学习资料类型")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class LearnDataType extends BaseEntity {

    //类型
    private String type;

    //上一级分类类型
    @ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id")
    private LearnDataType parent;

    //下级分类类型
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    private Set<LearnDataType> children = new HashSet<>(0);

}

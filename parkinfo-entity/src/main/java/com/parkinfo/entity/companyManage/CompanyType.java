package com.parkinfo.entity.companyManage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:25
 */
@EqualsAndHashCode(callSuper = true,exclude = {"parent","children"})
@Data
@Entity(name = "c_company_type")
@org.hibernate.annotations.Table(appliesTo = "c_company_type",comment = "供需类型表")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class CompanyType extends BaseEntity {

    //类型名称
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private CompanyType parent;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
    @JsonIgnoreProperties("parent")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<CompanyType> children = new HashSet<>(0);

}

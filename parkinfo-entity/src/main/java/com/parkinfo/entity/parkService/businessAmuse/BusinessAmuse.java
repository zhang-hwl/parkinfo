package com.parkinfo.entity.parkService.businessAmuse;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_business_amuse")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "BusinessAmuse",description =  "商务与周边娱乐")
public class BusinessAmuse extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "type")
    @ApiModelProperty("企业类型  小类")
    private BusinessAmuseType type;

    @ApiModelProperty("企业logo")
    private String logo;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;
}

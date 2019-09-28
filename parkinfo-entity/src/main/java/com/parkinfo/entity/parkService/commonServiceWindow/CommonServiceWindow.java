package com.parkinfo.entity.parkService.commonServiceWindow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuseType;
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
@Table(name = "c_common_service_window")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "CommonServiceWindow",description =  "公共服务窗口")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class CommonServiceWindow extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "type")
    @ApiModelProperty("公共服务窗口  小类")
    private CommonServiceWindowType type;

    @ApiModelProperty("logo")
    private String logo;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;

    @ApiModelProperty("业务详情")
    @Column(columnDefinition = "text")
    private String businessDetails;

    @ApiModelProperty("备注")
    private String remark;
}

package com.parkinfo.entity.personalCloud;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_cloud_disk")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "CloudDisk", description = "个人云盘")
public class CloudDisk extends BaseEntity {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @ApiModelProperty(value = "文件地址")
    private String fileUrl;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty("用户")
    private ParkUser parkUser;
}

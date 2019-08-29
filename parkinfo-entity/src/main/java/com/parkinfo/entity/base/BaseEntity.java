package com.parkinfo.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(columnDefinition="VARCHAR(32)")
    private String id;

    //是否删除
    @Column(name = "`delete`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否删除")
    private Boolean delete;

    //是否禁用
    @Column(name = "`available`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否可用")
    private Boolean available;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}

package com.parkinfo.entity.parkService.learningData;

import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
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
@Table(name = "c_learning_data")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "LearningData", description = "学习资料")
public class LearningData extends BaseEntity {
    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("关联园区")
    @ManyToOne()
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ApiModelProperty("书籍分类")
    @ManyToOne()
    @JoinColumn(name = "type")
    private ArchiveInfoType archiveInfoType;

    @ApiModelProperty("书籍地址")
    @Column(columnDefinition = "text")
    private String filePath;

    @ApiModelProperty("书籍简介")
    private String description;
}

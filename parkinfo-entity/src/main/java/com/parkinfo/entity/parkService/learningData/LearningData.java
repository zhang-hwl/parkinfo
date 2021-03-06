package com.parkinfo.entity.parkService.learningData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.archiveInfo.ArchiveComment;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_learning_data")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "LearningData", description = "学习资料")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
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
    private LearnDataType learnDataType;

    @ApiModelProperty("书籍地址")
    @Column(columnDefinition = "text")
    private String filePath;

    @ApiModelProperty("书籍简介")
    private String description;

    @OneToMany
    @JoinColumn(name = "learnData_id")
    //评论
    private List<LearnDataComment> learnDataComments;

//    @OneToMany
//    @JoinColumn(name = "learnData_id")
//    //阅读记录
//    private List<LearnReadRecord> learnReadRecords;

}

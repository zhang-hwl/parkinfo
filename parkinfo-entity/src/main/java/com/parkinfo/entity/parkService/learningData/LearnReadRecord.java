package com.parkinfo.entity.parkService.learningData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Data
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "c_learn_read_record")
//@ApiModel(value = "LearnReadRecord", description = "学习资料-阅读记录")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
//public class LearnReadRecord extends BaseEntity {
//
//    @ApiModelProperty(value = "阅读人头像")
//    private String avatar;
//
//    @ApiModelProperty(value = "阅读人姓名")
//    private String nickname;
//
//    @ApiModelProperty(value = "书名")
//    private String bookName;
//
//    @ApiModelProperty(value = "阅读日期")
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date readDate;
//
//    //冗余文件id，分页查询用
//    private String fileId;
//}

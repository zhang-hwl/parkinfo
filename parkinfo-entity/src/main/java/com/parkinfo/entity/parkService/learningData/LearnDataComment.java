package com.parkinfo.entity.parkService.learningData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_learn_comment")
@ApiModel(value = "LearnDataComment", description = "学习资料-用户评论")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class LearnDataComment extends BaseEntity {

    @ApiModelProperty(value = "评论内容")
    private String comment;

    @ApiModelProperty(value = "评论人姓名")
    private String nickname;

    @ApiModelProperty(value = "评论人头像")
    private String avatar;

}

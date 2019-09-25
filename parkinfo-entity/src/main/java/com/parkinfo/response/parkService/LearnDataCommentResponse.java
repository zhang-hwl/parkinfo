package com.parkinfo.response.parkService;

import com.parkinfo.entity.archiveInfo.ArchiveComment;
import com.parkinfo.entity.parkService.learningData.LearnDataComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LearnDataCommentResponse", description = "学习资料评论返回数据")
public class LearnDataCommentResponse extends LearningDateResponse{

    @ApiModelProperty(value = "评论")
    private List<LearnDataComment> learnDataComments;

}

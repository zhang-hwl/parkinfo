package com.parkinfo.response.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ArchiveInfoCommentResponse", description = "存档资料评论返回数据")
public class ArchiveInfoCommentResponse extends ArchiveInfoResponse {

    @ApiModelProperty(value = "评论")
    private List<ArchiveComment> archiveComments;

}

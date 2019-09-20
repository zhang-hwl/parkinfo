package com.parkinfo.request.archiveInfo;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArchiveCommentRequest {

    @NotBlank(message = "文件ID不能为空")
    @ApiModelProperty(value = "文件ID")
    private String archiveId;

    @ApiModelProperty(value = "评论")
    private String remark;

}

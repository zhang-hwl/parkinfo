package com.parkinfo.entity.notice;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.OneToOne;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo"})
@ApiModel(value = "SystemNotice", description = "系统公告")
public class SystemNotice extends BaseEntity {

    @ApiModelProperty(value = "封面图")
    private List<String> cover;

    @ApiModelProperty(value = "标题")
    private List<String> title;

    @ApiModelProperty(value = "概要")
    private List<String> profile;

    @ApiModelProperty(value = "富文本")
    private String text;

    @OneToOne
    private ParkInfo parkInfo;

}

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
public class SystemNotice {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "概要")
    private String profile;

    @ApiModelProperty(value = "富文本")
    private String text;

}

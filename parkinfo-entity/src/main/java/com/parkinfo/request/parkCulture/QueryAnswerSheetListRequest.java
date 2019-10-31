package com.parkinfo.request.parkCulture;

import com.parkinfo.entity.parkCulture.AnswerSheet;
import com.parkinfo.enums.AnswerSheetType;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 16:33
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryAnswerSheetListRequest extends PageRequest {

    @ApiModelProperty(value = "是否开始")
    private Boolean start;

    @NotNull(message = "试卷类型不能为空")
    private AnswerSheetType answerSheetType;

    @ApiModelProperty(value = "试卷名称")
    @Length(min = 0,max = 100,message = "试卷名称不能超过100个字符")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}

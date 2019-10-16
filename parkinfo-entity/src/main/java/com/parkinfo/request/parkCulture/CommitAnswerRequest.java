package com.parkinfo.request.parkCulture;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 17:01
 **/
@Data
public class CommitAnswerRequest {


    @ApiModelProperty(value = "答卷id")
    private String answerSheetId;

    @ApiModelProperty(value = "是否交卷")
    @NotNull(message = "是否交卷不能为空")
    private Boolean commit;

    @ApiModelProperty(value = "k为题目id,v为答案（判断为T/F）")
    private Map<String,String> answers;

//    @ApiModelProperty(hidden = true)
    public String convertAnswersToJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (answers.size() > 0) {
            try {
                return objectMapper.writeValueAsString(getAnswers());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return "{}";
            }
        } else {
            return "{}";
        }
    }

    public void convertAnswersFromJsonString(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (StringUtils.isNotBlank(json)) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, String> readValue = objectMapper.readValue(json, Map.class);
                setAnswers(readValue);
            } catch (IOException ignored) {
            }
        }
    }
}

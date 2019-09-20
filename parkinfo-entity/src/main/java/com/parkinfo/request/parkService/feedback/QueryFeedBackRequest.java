package com.parkinfo.request.parkService.feedback;

import com.parkinfo.request.base.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFeedBackRequest extends PageRequest {

    private String keyWords;

}

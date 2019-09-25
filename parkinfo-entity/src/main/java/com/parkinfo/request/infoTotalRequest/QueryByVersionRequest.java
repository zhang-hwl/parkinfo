package com.parkinfo.request.infoTotalRequest;

import com.parkinfo.request.base.PageRequest;
import lombok.Data;

@Data
public class QueryByVersionRequest extends PageRequest {

    private String version;

}

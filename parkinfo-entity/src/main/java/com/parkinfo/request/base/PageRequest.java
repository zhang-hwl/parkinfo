package com.parkinfo.request.base;

import lombok.Data;

@Data
public class PageRequest {

    protected Integer pageNum=1;

    protected Integer pageSize=10;
}

package com.parkinfo.web.parkService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkService/businessAmuse")
@Api(value = "/parkService/businessAmuse", tags = {"园区服务-商务&周边娱乐"})
public class BusinessAmuseController {
}

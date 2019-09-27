package com.parkinfo.web.homePage;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/report")
@Api(value = "/home/report", tags = {"首页-报表"})
public class HomeReportController {
}

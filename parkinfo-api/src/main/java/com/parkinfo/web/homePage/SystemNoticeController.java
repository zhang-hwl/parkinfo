package com.parkinfo.web.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.notice.SystemNotice;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
//@Api(value = "SystemNoticeController", tags = {"公告维护"})
public class SystemNoticeController {

//    @PostMapping("/update/homePage")
    @ApiOperation(value = "更新公告")
    public Result<String> updateNotice(@RequestBody SystemNotice systemNotice){
        return null;
    }

//    @PostMapping("/find/{id}")
    @ApiOperation(value = "查询公告")
    public Result<SystemNotice> findNotice(@PathVariable("id") String id){
        return null;
    }

}

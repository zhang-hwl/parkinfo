package com.parkinfo.web.login;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.request.login.QueryUserCurrentRequest;
import com.parkinfo.response.login.ParkUserResponse;
import com.parkinfo.service.login.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/login/login")
@Api(value = "/login/login", tags = {"登录模块"})
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/find")
    @ApiOperation(value = "获取园区列表")
    public Result<List<ParkInfo>> findAllPark() {
        return loginService.findAllPark();
    }

    @PostMapping("/search")
    @ApiOperation(value = "获取本园区用户")
    public Result<Page<ParkUserResponse>> findByCurrent(@RequestBody QueryUserCurrentRequest request) {
        return loginService.findByCurrent(request);
    }

    @PostMapping("/query")
    @ApiOperation(value = "根据园区获取用户")
    public Result<Page<ParkUserResponse>> query(@RequestBody QueryUserByParkRequest request) {
        return loginService.query(request);
    }
}

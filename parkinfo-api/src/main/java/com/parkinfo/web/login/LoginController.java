package com.parkinfo.web.login;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.response.login.ParkUserResponse;
import com.parkinfo.service.login.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/login/login")
@Api(value = "/login/login", tags = {"登录模块"})
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<String> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return loginService.login(request);
    }

    @PostMapping("/find")
    @ApiOperation(value = "获取园区列表")
    public Result<List<ParkInfo>> findAllPark() {
        return loginService.findAllPark();
    }

    @PostMapping("/search")
    @ApiOperation(value = "获取本园区用户")
    public Result<List<ParkUser>> findByCurrent() {
        return loginService.findByCurrent();
    }

    @PostMapping("/query/{parkId}")
    @ApiOperation(value = "根据园区获取用户")
    public Result<List<ParkUser>> query(@PathVariable String parkId) {
        return loginService.query(parkId);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据园区分页获取用户")
    public Result<Page<ParkUserResponse>> search(@RequestBody QueryUserByParkRequest request) {
        return loginService.search(request);
    }
}

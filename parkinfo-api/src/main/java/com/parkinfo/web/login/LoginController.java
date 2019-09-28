package com.parkinfo.web.login;

import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.response.login.LoginResponse;
import com.parkinfo.response.login.ParkUserResponse;
import com.parkinfo.service.login.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<LoginResponse>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return loginService.login(request);
    }

    @PostMapping("/choosePark/{parkId}")
    @RequiresAuthentication
    @ApiOperation(value = "选择园区")
    public Result<String> choosePark(@PathVariable("parkId")String parkId) {
        return loginService.choosePark(parkId);
    }


    @PostMapping("/searchAll")
    @ApiOperation(value = "根据园区分页获取用户")
    public Result<Page<ParkUserResponse>> search(@RequestBody QueryUserByParkRequest request) {
        return loginService.search(request);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "根据园区获取用户")
    public Result<List<ParkUserResponse>> findAll() {
        return loginService.findAll();
    }

    @PostMapping("/userInfo")
    @ApiOperation(value = "获取用户信息")
    public Result<ParkUserDTO> getUserInfo(){
        return loginService.getUserInfo();
    }
}

package com.parkinfo.web.sysConfig;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.sysConfig.AddUserRequest;
import com.parkinfo.request.sysConfig.QuerySysUserRequest;
import com.parkinfo.request.sysConfig.SetUserRequest;
import com.parkinfo.response.sysConfig.SysUserResponse;
import com.parkinfo.service.sysConfig.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/sysConfig/sysUser")
@Api(value = "/sysConfig/sysUser", tags = {"系统设置-用户管理"})
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/search")
    @ApiOperation(value = "分页查询用户列表",notes = "起始页为0")
    public Result<Page<SysUserResponse>> searchUser(@RequestBody QuerySysUserRequest request) {
        return sysUserService.searchUser(request);
    }

    @PostMapping("/avatar/upload")
    @ApiOperation(value = "用户头像上传，返回图片url")
    public Result<String> avatarUpload(HttpServletRequest request) {
        return sysUserService.avatarUpload(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public Result addUser(@Valid @RequestBody AddUserRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return sysUserService.addUser(request);
    }

    @PostMapping("/set")
    @ApiOperation(value = "编辑用户")
    public Result setUser(@Valid @RequestBody SetUserRequest request,BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return sysUserService.setUser(request);
    }

    @PostMapping("/disable/{id}")
    @ApiOperation(value = "启用或禁用")
    public Result disableUser(@PathVariable String id) {
        return sysUserService.disableUser(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "逻辑删除用户")
    public Result deleteUser(@PathVariable String id) {
        return sysUserService.deleteUser(id);
    }
}


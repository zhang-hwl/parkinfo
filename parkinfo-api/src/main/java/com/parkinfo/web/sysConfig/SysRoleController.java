package com.parkinfo.web.sysConfig;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.request.sysConfig.QuerySysRoleRequest;
import com.parkinfo.request.sysConfig.SetPermissionRequest;
import com.parkinfo.service.sysConfig.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysConfig/sysRole")
@Api(value = "/sysConfig/sysRole", tags = {"系统设置-角色管理"})
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @PostMapping("/permissions")
    @ApiOperation(value = "获取所有权限")
    public Result<List<ParkPermission>> findPermission() {
        return sysRoleService.findPermission();
    }

    @PostMapping("/search")
    @ApiOperation(value = "分页查询角色列表",notes = "起始页为0")
    public Result<Page<ParkRole>> findRole(@RequestBody QuerySysRoleRequest request) {
        return sysRoleService.findRole(request);
    }

    @PostMapping("/set")
    @ApiOperation(value = "设置角色所对应的权限")
    public Result setPermissions(@RequestBody SetPermissionRequest request) {
        return sysRoleService.setPermissions(request);
    }

    @PostMapping("/query")
    @ApiOperation(value = "获取所有角色不分页")
    public Result<List<ParkRole>> getAllRole() {
        return sysRoleService.getAllRole();
    }
}
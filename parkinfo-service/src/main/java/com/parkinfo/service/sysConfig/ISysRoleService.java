package com.parkinfo.service.sysConfig;


import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.request.sysConfig.QuerySysRoleRequest;
import com.parkinfo.request.sysConfig.SetPermissionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISysRoleService {

    /**
     * 查询所有权限
     * @return
     */
    Result<List<ParkPermission>> findPermission();

    /**
     * 分页查询角色列表
     * @param request  查询条件
     * @return
     */
    Result<Page<ParkRole>> findRole(QuerySysRoleRequest request);

    /**
     * 设置角色所属权限列表
     * @param request 权限id组及角色id
     * @return
     */
    Result setPermissions(SetPermissionRequest request);

    /**
     * 查询所有角色
     * @return
     */
    Result<List<ParkRole>> getAllRole();
}

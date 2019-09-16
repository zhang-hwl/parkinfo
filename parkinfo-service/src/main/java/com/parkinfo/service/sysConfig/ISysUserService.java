package com.parkinfo.service.sysConfig;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.sysConfig.AddUserRequest;
import com.parkinfo.request.sysConfig.QuerySysUserRequest;
import com.parkinfo.request.sysConfig.SetUserRequest;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface ISysUserService {
    /**
     * 分页查询后台用户
     * @param request 查询条件
     * @return
     */
    Result<Page<ParkUser>> searchUser(QuerySysUserRequest request);

    /**
     * 用户头像上传
     * @return
     */
    Result<String> avatarUpload(HttpServletRequest request);

    /**
     * 添加用户
     * @param request
     * @return
     */
    Result addUser(AddUserRequest request);

    /**
     * 编辑用户
     * @param request
     * @return
     */
    Result setUser(SetUserRequest request);

    /**
     * 禁用或启用用户
     * @param id
     * @return
     */
    Result disableUser(String id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Result deleteUser(String id);

}

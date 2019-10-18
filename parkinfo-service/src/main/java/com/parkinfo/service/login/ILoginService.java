package com.parkinfo.service.login;

import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.login.ChangePasswordRequest;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.request.login.SetUserInfoRequest;
import com.parkinfo.response.login.LoginResponse;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILoginService {

    Result<LoginResponse> login(LoginRequest request);

    Result<String> choosePark(String parkId);

    Result<Page<ParkUserResponse>> search(QueryUserByParkRequest request);

    Result<List<ParkUserResponse>> findAll();

    Result<ParkUserDTO> getUserInfo();

    /**
     * 用户修改自己的密码
     * @param request
     * @return
     */
    Result changePassword(ChangePasswordRequest request);

    /**
     * 修改用户个人信息
     * @param request
     * @return
     */
    Result setUserInfo(SetUserInfoRequest request);
}

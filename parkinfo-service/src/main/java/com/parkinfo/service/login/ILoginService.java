package com.parkinfo.service.login;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
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
}

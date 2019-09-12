package com.parkinfo.service.login;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.request.login.QueryUserCurrentRequest;
import com.parkinfo.response.login.ParkUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILoginService {

    Result<String> login(LoginRequest request);

    Result<List<ParkInfo>> findAllPark();

    Result<Page<ParkUserResponse>> findByCurrent(QueryUserCurrentRequest request);

    Result<Page<ParkUserResponse>> query(QueryUserByParkRequest request);

    Result<List<ParkPermission>> findPermission();
}

package com.parkinfo.service.login;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.response.login.ParkUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILoginService {

    Result<String> login(LoginRequest request);

    Result<List<ParkInfo>> findAllPark();

    Result<List<ParkUser>> findByCurrent();

    Result<List<ParkUser>> query(String parkId);

    Result<Page<ParkUserResponse>> search(QueryUserByParkRequest request);
}

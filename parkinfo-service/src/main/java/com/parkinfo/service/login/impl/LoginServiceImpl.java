package com.parkinfo.service.login.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.request.login.QueryUserCurrentRequest;
import com.parkinfo.response.login.ParkUserResponse;
import com.parkinfo.service.login.ILoginService;
import com.parkinfo.token.TokenUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<String> login(LoginRequest request) {
        String token;
        Optional<ParkUser> optionalParkUser = parkUserRepository.findByAccountAndAvailableIsTrueAndDeleteIsFalse(request.getAccount());
        if (!optionalParkUser.isPresent()) {
            throw new NormalException("用户不存在");
        }
        ParkUser parkUser = optionalParkUser.get();
        parkUser.setRoles(null);
        String password = new SimpleHash("MD5", request.getPassword(), parkUser.getSalt(), 1024).toHex();
        if (!password.equals(parkUser.getPassword())) {
            throw new NormalException("密码错误");
        }
        //TODO
        Optional<ParkInfo> optionalParkInfo = parkInfoRepository.findByIdAndDeleteIsFalse(request.getId());
        if (!optionalParkInfo.isPresent()) {
            throw new NormalException("园区不存在");
        }
        ParkInfo park = optionalParkInfo.get();
        if (null == parkUser.getParks()) {
            throw new NormalException("请绑定园区");
        }
        if (!parkUser.getParks().contains(park)) {
            throw new NormalException("请选择所在园区");
        }
        token = tokenUtils.generateTokeCode(parkUser, request.getId());
        return Result.<String>builder().success().data(token).build();
    }

    @Override
    public Result<List<ParkInfo>> findAllPark() {
        ParkInfo exampleData = new ParkInfo();
        ExampleMatcher matcher = ExampleMatcher.matching();
        exampleData.setDelete(false);
        exampleData.setAvailable(true);
        Example<ParkInfo> example = Example.of(exampleData, matcher);
        List<ParkInfo> parkInfos = parkInfoRepository.findAll(example, Sort.by(Sort.Direction.DESC, "createTime"));
        return Result.<List<ParkInfo>>builder().success().data(parkInfos).build();
    }

    @Override
    public Result<List<ParkUser>> findByCurrent() {
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        List<ParkUser> allByParksEquals = parkUserRepository.findAllByParksEqualsAndDeleteIsFalseAndAvailableIsTrue(parkInfo);
        return Result.<List<ParkUser>>builder().success().data(allByParksEquals).build();
    }

    @Override
    public Result<List<ParkUser>> query(String parkId) {
        ParkInfo parkInfo = this.checkPark(parkId);
        /*List<ParkUser> response = new ArrayList<>();
        ParkUser user = new ParkUser();
        List<ParkUser> collect = parkInfo.getUsers().stream()
                .filter(parkUser -> parkUser.getDelete().equals(false)&&parkUser.getAvailable().equals(true))
                .collect(Collectors.toList());
        collect.forEach(parkUser -> {
            BeanUtils.copyProperties(parkUser, user);
            response.add(user);
        });*/
        List<ParkUser> allByParksEquals = parkUserRepository.findAllByParksEqualsAndDeleteIsFalseAndAvailableIsTrue(parkInfo);
        return Result.<List<ParkUser>>builder().success().data(allByParksEquals).build();
    }

    private ParkInfo checkPark(String id) {
        Optional<ParkInfo> parkInfoOptional = parkInfoRepository.findFirstByDeleteIsFalseAndAvailableIsTrueAndId(id);
        if (!parkInfoOptional.isPresent()) {
            throw new NormalException("园区不存在");
        }
        return parkInfoOptional.get();
    }
}

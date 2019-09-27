package com.parkinfo.service.login.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.login.LoginRequest;
import com.parkinfo.request.login.QueryUserByParkRequest;
import com.parkinfo.response.login.LoginResponse;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserResponse;
import com.parkinfo.service.login.ILoginService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
    public Result<LoginResponse> login(LoginRequest request) {
        Optional<ParkUser> optionalParkUser = parkUserRepository.findByAccountAndAvailableIsTrueAndDeleteIsFalse(request.getAccount());
        if (!optionalParkUser.isPresent()) {
            throw new NormalException("用户不存在");
        }
        ParkUser parkUser = optionalParkUser.get();
        String password = new SimpleHash("MD5", request.getPassword(), parkUser.getSalt(), 1024).toHex();
        if (!password.equals(parkUser.getPassword())) {
            throw new NormalException("密码错误");
        }
        String token= tokenUtils.generateTokeCode(parkUser, null);
        List<ParkInfo> parkInfoList = new ArrayList<>(parkUser.getParks());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        List<ParkInfoListResponse> parkList  = this.convertParkInfoList(parkInfoList);
        response.setParkList(parkList);
        return Result.<LoginResponse>builder().success().data(response).build();
    }

    @Override
    public Result<String> choosePark(String parkId) {
//        ParkUser parkUser = tokenUtils.getLoginUser();
        ParkUserDTO parkUserDTO = tokenUtils.getLoginUserDTO();
        Optional<ParkUser> parkUserOptional = parkUserRepository.findById(parkUserDTO.getId());
        if (!parkUserOptional.isPresent()){
            throw new NormalException("用户不存在");
        }
        ParkUser parkUser = parkUserOptional.get();
        List<ParkInfo> parkInfoList = new ArrayList<>(parkUser.getParks());
        List<String> parkIds = parkInfoList.stream().map(ParkInfo::getId).collect(Collectors.toList());
        if (!parkIds.contains(parkId)){
            throw new NormalException("该账号不属于该园区");
        }
        String token = tokenUtils.refreshToken(parkId);
        return Result.<String>builder().success().data(token).build();
    }

    @Override
    public Result<Page<ParkUserResponse>> search(QueryUserByParkRequest request) {
        request.setId(tokenUtils.getCurrentParkInfo().getId());
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkUser> specification = (Specification<ParkUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<ParkUser, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parks", ParkInfo.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), request.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ParkUser> parkUsers = parkUserRepository.findAll(specification, pageable);
        Page<ParkUserResponse> userResponses = this.convertUserPage(parkUsers);
        return Result.<Page<ParkUserResponse>>builder().success().data(userResponses).build();
    }

    @Override
    public Result<List<ParkUserResponse>> findAll() {
        String id = tokenUtils.getCurrentParkInfo().getId();
        List<String> userIds = parkUserRepository.fingAllByParkInfoId(id);
        List<ParkUserResponse> result = Lists.newArrayList();
        userIds.forEach(temp -> {
            ParkUser parkUser = checkParkUser(temp);
            ParkUserResponse response = new ParkUserResponse();
            BeanUtils.copyProperties(parkUser, response);
            result.add(response);
        });
        return Result.<List<ParkUserResponse>>builder().success().data(result).build();
    }

    private ParkUser checkParkUser(String id){
        Optional<ParkUser> byIdAndDeleteIsFalse = parkUserRepository.findByIdAndDeleteIsFalse(id);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("用户不存在");
        }
        return byIdAndDeleteIsFalse.get();
    }

    private ParkInfo checkPark(String id) {
        Optional<ParkInfo> parkInfoOptional = parkInfoRepository.findFirstByDeleteIsFalseAndAvailableIsTrueAndId(id);
        if (!parkInfoOptional.isPresent()) {
            throw new NormalException("园区不存在");
        }
        return parkInfoOptional.get();
    }

    private Page<ParkUserResponse> convertUserPage(Page<ParkUser> parkUserPage) {
        List<ParkUserResponse> content = new ArrayList<>();
        parkUserPage.getContent().forEach(parkUser -> {
            ParkUserResponse response = new ParkUserResponse();
            BeanUtils.copyProperties(parkUser, response);
            content.add(response);
        });
        return new PageImpl<>(content, parkUserPage.getPageable(), parkUserPage.getTotalElements());
    }

    private List<ParkInfoListResponse> convertParkInfoList(List<ParkInfo> parkInfoList) {
        List<ParkInfoListResponse> responseList = Lists.newArrayList();
        parkInfoList.forEach(parkInfo -> {
            ParkInfoListResponse response = new ParkInfoListResponse();
            BeanUtils.copyProperties(parkInfo,response);
            responseList.add(response);
        });
        return responseList;
    }
}

package com.parkinfo.service.login.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkPermission;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkPermissionRepository;
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

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ParkInfoRepository parkInfoRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private ParkPermissionRepository parkPermissionRepository;

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
        String password = new SimpleHash("MD5", request.getPassword(), parkUser.getSalt(),1024).toHex();
        if (!password.equals(parkUser.getPassword())){
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
        if (!parkUser.getParks().contains(park)){
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
    public Result<Page<ParkUserResponse>> findByCurrent(QueryUserCurrentRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkUser> specification = (Specification<ParkUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            Join<ParkUser, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parkInfo", ParkInfo.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), parkInfo.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("entered").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ParkUser> parkUserPage = parkUserRepository.findAll(specification, pageable);
        Page<ParkUserResponse> responses = this.convertUserPage(parkUserPage);
        return Result.<Page<ParkUserResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<Page<ParkUserResponse>> query(QueryUserByParkRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkUser> specification = (Specification<ParkUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<ParkUser, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parkInfo", ParkInfo.class), JoinType.LEFT);
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
    public Result<List<ParkPermission>> findPermission() {
        List<ParkPermission> permissionList = parkPermissionRepository.findAllByParentIsNullAndAvailableIsTrueAndDeleteIsFalse();
        return Result.<List<ParkPermission>>builder().success().data(permissionList).build();
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
}

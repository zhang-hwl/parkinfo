package com.parkinfo.service.sysConfig.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.sysConfig.AddSysParkRequest;
import com.parkinfo.request.sysConfig.QuerySysParkRequest;
import com.parkinfo.response.sysConfig.SysParkInfoResponse;
import com.parkinfo.service.sysConfig.ISysParkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class SysParkServiceImpl implements ISysParkService {

    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<Page<SysParkInfoResponse>> findAll(QuerySysParkRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkInfo> specification = new Specification<ParkInfo>() {
            @Override
            public Predicate toPredicate(Root<ParkInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if(StringUtils.isNotBlank(request.getParkName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), request.getParkName()));
                }
                predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
                predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<ParkInfo> all = parkInfoRepository.findAll(specification, pageable);
        List<SysParkInfoResponse> list = Lists.newArrayList();
        all.getContent().forEach(temp -> {
            SysParkInfoResponse response = new SysParkInfoResponse();
            BeanUtils.copyProperties(temp, response);
            response.setUserId(temp.getManager().getId());
            response.setUserName(temp.getManager().getNickname());
            list.add(response);
        });
        Page<SysParkInfoResponse> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<SysParkInfoResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<String> addPark(AddSysParkRequest request) {
        Optional<ParkInfo> byName = parkInfoRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(request.getParkName());
        if(byName.isPresent()){
            throw new NormalException("园区名称已存在");
        }
        ParkInfo parkInfo = new ParkInfo();
        parkInfo.setName(request.getParkName());
//        Optional<ParkUser> byId = parkUserRepository.findByIdAndAvailableIsTrueAndDeleteIsFalse(request.getUserId());
//        if(!byId.isPresent()){
//            throw new NormalException("用户不存在");
//        }
//        ParkUser parkUser = byId.get();
//        parkInfo.setManager(parkUser);
        parkInfo.setDelete(false);
        parkInfo.setAvailable(true);
        BeanUtils.copyProperties(request, parkInfo);
        parkInfoRepository.save(parkInfo);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editPark(String id, AddSysParkRequest request) {
        Optional<ParkInfo> byParkId = parkInfoRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byParkId.isPresent()){
            throw new NormalException("园区不存在");
        }
        ParkInfo parkInfo = byParkId.get();
        if(!parkInfo.getName().equals(request.getParkName())){
            Optional<ParkInfo> byName = parkInfoRepository.findByNameAndDeleteIsFalseAndAvailableIsTrue(request.getParkName());
            if(byName.isPresent()){
                throw new NormalException("园区名称已存在");
            }
        }
        parkInfo.setName(request.getParkName());
        if(StringUtils.isNotBlank(request.getUserId())){
            Optional<ParkUser> byId = parkUserRepository.findByIdAndAvailableIsTrueAndDeleteIsFalse(request.getUserId());
            if(!byId.isPresent()){
                throw new NormalException("用户不存在");
            }
            ParkUser parkUser = byId.get();
            parkInfo.setManager(parkUser);
        }
        BeanUtils.copyProperties(request, parkInfo);
        parkInfoRepository.save(parkInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<String> deletePark(String id) {
        Optional<ParkInfo> byId = parkInfoRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        ParkInfo parkInfo = byId.get();
        Boolean available = parkInfo.getAvailable();
        parkInfo.setAvailable(!available);
        parkInfoRepository.save(parkInfo);
        return Result.<String>builder().success().data("改变状态成功").build();
    }
}

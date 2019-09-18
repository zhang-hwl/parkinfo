package com.parkinfo.service.archiveInfo.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyPaperService extends ArchiveInfoServiceImpl {

    @Autowired
    private ArchiveInfoRepository archiveInfoRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ArchiveInfoTypeRepository archiveInfoTypeRepository;

    @Override
    public Result<Page<ArchiveInfoResponse>> search(QueryArchiveInfoRequest request) {
        ParkUserDTO loginUserDTO = tokenUtils.getLoginUserDTO();
        if(loginUserDTO == null){
            throw new NormalException("token不存在或已过期");
        }
        String parkId = loginUserDTO.getCurrentParkId();
        Optional<ArchiveInfoType> byType = archiveInfoTypeRepository.findByTypeAndDeleteIsFalseAndAvailableIsTrue(request.getGeneral());
        List<String> roles = loginUserDTO.getRole();
        ArchiveInfoType archiveInfoType = byType.get();
        String general = archiveInfoType.getId();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<ArchiveInfo> specification = new Specification<ArchiveInfo>() {
            @Override
            public Predicate toPredicate(Root<ArchiveInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(request.getGeneral())){
                    predicates.add(cb.equal(root.get("generalId").as(String.class), general)); //文件大类
                }
                if(StringUtils.isNotBlank(request.getKind())){
                    predicates.add(cb.equal(root.get("kind").get("id").as(String.class), request.getKind())); //文件种类
                }
                if(StringUtils.isNotBlank(request.getParkId())){
                    predicates.add(cb.equal(root.get("parkInfo").get("id").as(String.class), request.getParkId())); //园区ID
                }
                if(StringUtils.isNotBlank(request.getFileName())){
                    predicates.add(cb.like(root.get("fileName").as(String.class), "%"+request.getFileName()+"%"));  //根据文件名模糊查询
                }
                if(request.getStartTime() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getStartTime()));  //大于等于开始时间
                }
                if(request.getEndTime() != null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getEndTime())); //小于等于结束时间
                }
                if(StringUtils.isNotBlank(request.getRemark())){
                    predicates.add(cb.like(root.get("remark").as(String.class), "%"+request.getRemark()+"%")); //文档说明
                }
                if(request.getExternal() != null){
                    predicates.add(cb.equal(root.get("external").as(Boolean.class), request.getExternal())); //是否对外
                }
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), false));  //没有被删除
                predicates.add(cb.equal(root.get("available").as(Boolean.class), true));  //可用
                List<Predicate> list = Lists.newArrayList();
                if(!roles.contains(ParkRoleEnum.PRESIDENT.name()) && !roles.contains(ParkRoleEnum.GENERAL_MANAGER.name()) && !roles.contains(ParkRoleEnum.PARK_MANAGER.name())){
                    if(roles.contains(ParkRoleEnum.OFFICER.name())){
                        list.add(cb.equal(root.get("government").as(Boolean.class), true));
                    }
                    if(roles.contains(ParkRoleEnum.HR_USER.name())){
                        list.add(cb.equal(root.get("hrOrgan").as(Boolean.class), true));
                    }
                    if(roles.contains(ParkRoleEnum.PARK_USER.name())){
                        //本园区员工可见
                        list.add(cb.and(cb.equal(root.get("parkPerson").as(Boolean.class), true), cb.equal(root.get("parkInfo").get("id").as(String.class), parkId)));
                        //本园区员工不可见，其他园区员工可见
                        list.add(cb.and(cb.equal(root.get("parkPerson").as(Boolean.class), false), cb.notEqual(root.get("parkInfo").get("id").as(String.class), parkId), cb.equal(root.get("otherParkPerson").as(Boolean.class), true)));
                    }
                    predicates.add(cb.or(list.toArray(new Predicate[list.size()])));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ArchiveInfo> all = archiveInfoRepository.findAll(specification, pageable);
        List<ArchiveInfoResponse> response = new ArrayList<>();
        all.getContent().forEach(temp->{
            ArchiveInfoResponse archiveInfoResponse = new ArchiveInfoResponse();
            BeanUtils.copyProperties(temp, archiveInfoResponse);
            response.add(archiveInfoResponse);
        });
        Page<ArchiveInfoResponse> result = new PageImpl<>(response, all.getPageable(), all.getTotalElements());
        BeanUtils.copyProperties(all, result);
        return Result.<Page<ArchiveInfoResponse>>builder().success().data(result).build();
    }

}

package com.parkinfo.service.companyManage.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.companyManage.ConnectDetail;
import com.parkinfo.entity.companyManage.DiscussDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.companyManage.ConnectDetailRepository;
import com.parkinfo.repository.companyManage.DiscussDetailRepository;
import com.parkinfo.request.compayManage.QueryManagementRequest;
import com.parkinfo.request.compayManage.SetInvestmentRequest;
import com.parkinfo.response.companyManage.ManagementResponse;
import com.parkinfo.service.companyManage.IManagementService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ManagementServiceImpl implements IManagementService {
    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    @Autowired
    private ConnectDetailRepository connectDetailRepository;

    @Autowired
    private DiscussDetailRepository discussDetailRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<ManagementResponse>> findAll(QueryManagementRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<CompanyDetail> specification = (Specification<CompanyDetail>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
            if (StringUtils.isNotBlank(request.getLinkMan())) {
                predicates.add(criteriaBuilder.like(root.get("linkMan").as(String.class), "%" + request.getLinkMan() + "%"));
            }
            if (StringUtils.isNotBlank(request.getConnectWay())) {
                Join<CompanyDetail, DiscussDetail> join1 = root.join(root.getModel().getSingularAttribute("discussDetail", DiscussDetail.class), JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(join1.get("connectWay").as(String.class), request.getConnectWay()));
            }
            if (null != request.getDiscussStatus()) {
                Join<CompanyDetail, DiscussDetail> join2 = root.join(root.getModel().getSingularAttribute("discussDetail", DiscussDetail.class), JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(join2.get("discussStatus").as(DiscussStatus.class), request.getDiscussStatus().ordinal()));
            }
            Join<CompanyDetail, ParkInfo> join = root.join(root.getModel().getSingularAttribute("parkInfo", ParkInfo.class), JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id").as(String.class), parkInfo.getId()));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CompanyDetail> companyDetailPage = companyDetailRepository.findAll(specification, pageable);
        Page<ManagementResponse> responses = this.convertDetailPage(companyDetailPage);
        return Result.<Page<ManagementResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result delete(String id) {
        CompanyDetail companyDetail = this.checkInvestment(id);
        companyDetail.setAvailable(false);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public Result set(SetInvestmentRequest request) {
        CompanyDetail companyDetail = this.checkInvestment(request.getId());
        BeanUtils.copyProperties(request,companyDetail);
        companyDetailRepository.save(companyDetail);
        return Result.builder().success().message("修改成功").build();
    }

    private Page<ManagementResponse> convertDetailPage(Page<CompanyDetail> companyDetailPage) {
        List<ManagementResponse> content = new ArrayList<>();
        companyDetailPage.getContent().forEach(companyDetail -> {
            ManagementResponse response = new ManagementResponse();
            BeanUtils.copyProperties(companyDetail,response);
            Optional<DiscussDetail> discussDetailOptional = discussDetailRepository.findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(companyDetail.getId());
            if (!discussDetailOptional.isPresent()) {
                throw new NormalException("洽淡详情不存在");
            }
            DiscussDetail discussDetail = discussDetailOptional.get();
            response.setConnectWay(discussDetail.getConnectWay());
            response.setDiscussStatus(discussDetail.getDiscussStatus());
            Optional<ConnectDetail> connectDetailOptional = connectDetailRepository.findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(companyDetail.getId());
            if (!connectDetailOptional.isPresent()) {
                throw new NormalException("对接详情不存在");
            }
            ConnectDetail connectDetail = connectDetailOptional.get();
            response.setConnectTime(connectDetail.getConnectTime());
            content.add(response);
        });
        return new PageImpl<>(content, companyDetailPage.getPageable(), companyDetailPage.getTotalElements());
    }

    private CompanyDetail checkInvestment(String id) {
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if (!companyDetailOptional.isPresent()) {
            throw new NormalException("招商信息不存在");
        }
        return companyDetailOptional.get();
    }
}

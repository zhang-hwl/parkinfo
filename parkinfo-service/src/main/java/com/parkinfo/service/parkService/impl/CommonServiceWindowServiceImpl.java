package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuse;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuseType;
import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindow;
import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.CommonServiceWindowRepository;
import com.parkinfo.repository.parkService.CommonServiceWindowTypeRepository;
import com.parkinfo.request.parkService.commonServiceWindow.AddCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.EditCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.SearchCommonServiceWindowRequest;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import com.parkinfo.response.parkService.CommonServiceWindowResponse;
import com.parkinfo.service.parkService.ICommonServiceWindowService;
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
import java.util.List;
import java.util.Optional;

@Service
public class CommonServiceWindowServiceImpl implements ICommonServiceWindowService {
    @Autowired
    private CommonServiceWindowRepository commonServiceWindowRepository;
    @Autowired
    private CommonServiceWindowTypeRepository commonServiceWindowTypeRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<CommonServiceWindowResponse>> searchCommonServiceWindow(SearchCommonServiceWindowRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        Specification<CommonServiceWindow> serviceWindowSpecification = (Specification<CommonServiceWindow>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getServerName())){
                predicates.add(criteriaBuilder.like(root.get("serviceName").as(String.class),"%"+request.getServerName()+"%"));
            }
            if (StringUtils.isNotBlank(request.getSmallTypeId())){
                predicates.add(criteriaBuilder.equal(root.get("type").as(CommonServiceWindowType.class),this.checkCommonServiceWindowType(request.getSmallTypeId())));
            }else if (StringUtils.isNotBlank(request.getBigTypeId())){
                CommonServiceWindowType commonServiceWindowType = this.checkCommonServiceWindowType(request.getBigTypeId());
                CriteriaBuilder.In<CommonServiceWindowType> type = criteriaBuilder.in(root.get("type").as(CommonServiceWindowType.class));
                commonServiceWindowType.getChildren().forEach(type::value);
                predicates.add(type);
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<CommonServiceWindow> commonServiceWindowPage =  commonServiceWindowRepository.findAll(serviceWindowSpecification,pageable);
        Page<CommonServiceWindowResponse> responses = this.convertCommonServiceWindowResponse(commonServiceWindowPage);
        return Result.<Page<CommonServiceWindowResponse>>builder().success().data(responses).build();
    }

    private Page<CommonServiceWindowResponse> convertCommonServiceWindowResponse(Page<CommonServiceWindow> commonServiceWindowPage) {
        List<CommonServiceWindowResponse> responses = Lists.newArrayList();
        commonServiceWindowPage.getContent().forEach(commonServiceWindow -> {
            CommonServiceWindowResponse response = new CommonServiceWindowResponse();
            BeanUtils.copyProperties(commonServiceWindow,response);
            responses.add(response);
        });
        return new PageImpl<>(responses,commonServiceWindowPage.getPageable(),commonServiceWindowPage.getTotalElements());
    }

    private CommonServiceWindowType checkCommonServiceWindowType(String id) {
        Optional<CommonServiceWindowType> commonServiceWindowTypeOptional = commonServiceWindowTypeRepository.findByDeleteIsFalseAndId(id);
        if (!commonServiceWindowTypeOptional.isPresent()){
            throw new NormalException("分类不存在");
        }
        return commonServiceWindowTypeOptional.get();
    }

    @Override
    public Result<String> addCommonServiceWindow(AddCommonServiceWindowRequest request) {
        CommonServiceWindow commonServiceWindow = new CommonServiceWindow();
        BeanUtils.copyProperties(request,commonServiceWindow);
        if (StringUtils.isNotBlank(request.getTypeId())){
            CommonServiceWindowType commonServiceWindowType = this.checkCommonServiceWindowType(request.getTypeId());
            commonServiceWindow.setType(commonServiceWindowType);
        }
        commonServiceWindow.setParkInfo(tokenUtils.getCurrentParkInfo());
        commonServiceWindow.setDelete(Boolean.FALSE);
        commonServiceWindow.setAvailable(Boolean.TRUE);
        commonServiceWindowRepository.save(commonServiceWindow);
        return Result.<String>builder().success().message("新增成功").build();
    }

    @Override
    public Result<String> editCommonServiceWindow(EditCommonServiceWindowRequest request) {
        CommonServiceWindow commonServiceWindow = this.checkCommonServiceWindow(request.getId());
        if (StringUtils.isNotBlank(request.getTypeId())){
            CommonServiceWindowType commonServiceWindowType = this.checkCommonServiceWindowType(request.getTypeId());
            commonServiceWindow.setType(commonServiceWindowType);
        }
        commonServiceWindow.setBusinessHours(request.getBusinessHours());
        commonServiceWindow.setServiceName(request.getServiceName());
        commonServiceWindow.setContactAddress(request.getContactAddress());
        commonServiceWindow.setContactNumber(request.getContactNumber());
        commonServiceWindow.setBusinessDetails(request.getServiceName());
        commonServiceWindow.setLogo(request.getLogo());
        commonServiceWindowRepository.save(commonServiceWindow);
        return Result.<String>builder().success().message("编辑成功").build();
    }

    private CommonServiceWindow checkCommonServiceWindow(String id) {
        Optional<CommonServiceWindow> commonServiceWindowOptional = commonServiceWindowRepository.findByDeleteIsFalseAndId(id);
        if (!commonServiceWindowOptional.isPresent()){
            throw new NormalException("公共服务窗口不存在");
        }
        return commonServiceWindowOptional.get();
    }

    @Override
    public Result<String> deleteCommonServiceWindow(String id) {
        CommonServiceWindow commonServiceWindow = this.checkCommonServiceWindow(id);
        commonServiceWindow.setDelete(Boolean.TRUE);
        commonServiceWindowRepository.save(commonServiceWindow);
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result<CommonServiceWindowResponse> detail(String id) {
        return null;
    }
}

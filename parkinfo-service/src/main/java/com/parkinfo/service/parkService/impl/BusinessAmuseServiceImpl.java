package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuse;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuseType;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.BusinessAmuseRepository;
import com.parkinfo.repository.parkService.BusinessAmuseTypeRepository;
import com.parkinfo.request.parkService.businessAmuse.AddBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.EditBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.SearchBusinessAmuseRequest;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import com.parkinfo.service.parkService.IBusinessAmuseService;
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
public class BusinessAmuseServiceImpl implements IBusinessAmuseService {
    @Autowired
    private BusinessAmuseRepository businessAmuseRepository;
    @Autowired
    private BusinessAmuseTypeRepository businessAmuseTypeRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<BusinessAmuseResponse>> searchBusinessAmuse(SearchBusinessAmuseRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        Specification<BusinessAmuse> specification = (Specification<BusinessAmuse>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getCompanyName())){
                predicates.add(criteriaBuilder.like(root.get("companyName").as(String.class),"%"+request.getCompanyName()+"%"));
            }
            if (StringUtils.isNotBlank(request.getSmallTypeId())){
                predicates.add(criteriaBuilder.equal(root.get("type").as(BusinessAmuseType.class),this.checkBusinessAmuseType(request.getSmallTypeId())));
            }else if (StringUtils.isNotBlank(request.getBigTypeId())){
                BusinessAmuseType businessAmuseType = this.checkBusinessAmuseType(request.getBigTypeId());
                CriteriaBuilder.In<BusinessAmuseType> type = criteriaBuilder.in(root.get("type").as(BusinessAmuseType.class));
                businessAmuseType.getChildren().forEach(type::value);
                predicates.add(type);
            }
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<BusinessAmuse> businessAmusePage = businessAmuseRepository.findAll(specification,pageable);
        Page<BusinessAmuseResponse> responses = this.convertBusinessAmuseResponse(businessAmusePage);
        return Result.<Page<BusinessAmuseResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<String> deleteBusinessAmuse(String id) {
        BusinessAmuse businessAmuse = this.checkBusinessAmuse(id);
        businessAmuse.setDelete(Boolean.TRUE);
        businessAmuseRepository.save(businessAmuse);
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result<String> editBusinessAmuse(EditBusinessAmuseRequest request) {
        BusinessAmuse businessAmuse = this.checkBusinessAmuse(request.getId());
        if (StringUtils.isNotBlank(request.getTypeId())){
            BusinessAmuseType businessAmuseType = this.checkBusinessAmuseType(request.getTypeId());
            businessAmuse.setType(businessAmuseType);
        }
        businessAmuse.setBusinessHours(request.getBusinessHours());
        businessAmuse.setCompanyName(request.getCompanyName());
        businessAmuse.setContactAddress(request.getContactAddress());
        businessAmuse.setContactNumber(request.getContactNumber());
        businessAmuse.setContacts(request.getContacts());
        businessAmuse.setLogo(request.getLogo());
        businessAmuseRepository.save(businessAmuse);
        return Result.<String>builder().success().message("编辑成功").build();
    }

    private BusinessAmuse checkBusinessAmuse(String id) {
        Optional<BusinessAmuse> businessAmuseOptional = businessAmuseRepository.findByDeleteIsFalseAndId(id);
        if (!businessAmuseOptional.isPresent()){
            throw new NormalException("服务商不存在");
        }
        return businessAmuseOptional.get();
    }

    @Override
    public Result<String> addBusinessAmuse(AddBusinessAmuseRequest request) {
        BusinessAmuse businessAmuse = new BusinessAmuse();
        BeanUtils.copyProperties(request,businessAmuse);
        if (StringUtils.isNotBlank(request.getTypeId())){
            BusinessAmuseType businessAmuseType = this.checkBusinessAmuseType(request.getTypeId());
            businessAmuse.setType(businessAmuseType);
        }
        businessAmuse.setParkInfo(tokenUtils.getCurrentParkInfo());
        businessAmuse.setDelete(Boolean.FALSE);
        businessAmuse.setAvailable(Boolean.TRUE);
        businessAmuseRepository.save(businessAmuse);
        return Result.<String>builder().success().message("新增成功").build();
    }



    private Page<BusinessAmuseResponse> convertBusinessAmuseResponse(Page<BusinessAmuse> businessAmusePage) {
        List<BusinessAmuseResponse> responses = Lists.newArrayList();
        businessAmusePage.getContent().forEach(businessAmuse -> {
            BusinessAmuseResponse response = new BusinessAmuseResponse();
            BeanUtils.copyProperties(businessAmuse,response);
            responses.add(response);
        });
        return new PageImpl<>(responses,businessAmusePage.getPageable(),businessAmusePage.getTotalElements());
    }

    private BusinessAmuseType checkBusinessAmuseType(String typeId) {
        Optional<BusinessAmuseType> businessAmuseTypeOptional = businessAmuseTypeRepository.findById(typeId);
        if (!businessAmuseTypeOptional.isPresent()){
            throw new NormalException("分类不存在");
        }
        return businessAmuseTypeOptional.get();
    }
}

package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.InfoTotalVersion;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.informationTotal.InfoVersionRepository;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;
import com.parkinfo.service.informationTotal.IInfoVersionService;
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
public class InfoVersionServiceImpl implements IInfoVersionService {

    @Autowired
    private InfoVersionRepository infoVersionRepository;

    @Override
    public Result<String> add(InfoVersionResponse response) {
        InfoTotalVersion version = new InfoTotalVersion();
        version.setGeneral(response.getGeneral());
        version.setVersion(response.getVersion());
        version.setDelete(false);
        version.setAvailable(true);
        infoVersionRepository.save(version);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<List<String>> findByGeneral(String general) {
        List<String> list = Lists.newArrayList();
        List<InfoTotalVersion> all = infoVersionRepository.findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(general);
        all.forEach(temp -> {
            list.add(temp.getVersion());
        });
        return Result.<List<String>>builder().success().data(list).build();
    }

    @Override
    public Result<String> delete(String id) {
        InfoTotalVersion version = checkInfoTotalVersion(id);
        version.setDelete(true);
        infoVersionRepository.save(version);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<Page<InfoVersionResponse>> findAll(InfoVersionRequest request) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<InfoTotalVersion> specification = new Specification<InfoTotalVersion>() {
            @Override
            public Predicate toPredicate(Root<InfoTotalVersion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if(StringUtils.isNotBlank(request.getType())){
                    predicates.add(criteriaBuilder.equal(root.get("general").as(String.class), request.getType()));
                }
                predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
                predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<InfoTotalVersion> all = infoVersionRepository.findAll(specification, pageable);
        List<InfoVersionResponse> list = Lists.newArrayList();
        all.getContent().forEach(temp -> {
            InfoVersionResponse response = new InfoVersionResponse();
            BeanUtils.copyProperties(temp, response);
            list.add(response);
        });
        Page<InfoVersionResponse> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<InfoVersionResponse>>builder().success().data(result).build();
    }

    private InfoTotalVersion checkInfoTotalVersion(String id){
        Optional<InfoTotalVersion> byId = infoVersionRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byId.isPresent()){
            throw new NormalException("类型不存在");
        }
        return byId.get();
    }

}

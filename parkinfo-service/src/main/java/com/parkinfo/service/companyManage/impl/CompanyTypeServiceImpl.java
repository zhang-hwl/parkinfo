package com.parkinfo.service.companyManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.CompanyType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyTypeRepository;
import com.parkinfo.request.compayManage.AddGeneralRequest;
import com.parkinfo.request.compayManage.AddKindTypeRequest;
import com.parkinfo.request.compayManage.EditTypeRequest;
import com.parkinfo.request.compayManage.SearchCompanyTypeRequest;
import com.parkinfo.response.companyManage.CompanyTypeDetailResponse;
import com.parkinfo.response.companyManage.CompanyTypeResponse;
import com.parkinfo.service.companyManage.ICompanyTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Li
 * description:
 * date: 2019-10-10 11:01
 */
@Service
public class CompanyTypeServiceImpl implements ICompanyTypeService {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public Result<Page<CompanyTypeResponse>> search(SearchCompanyTypeRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize());
        Specification<CompanyType> specification = (Specification<CompanyType>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(request.getName())){
                predicates.add(cb.equal(root.get("name").as(String.class), "%"+request.getName()+"%"));
            }
            predicates.add(cb.isNull(root.get("parent").as(CompanyType.class)));
            predicates.add(cb.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            predicates.add(cb.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<CompanyType> page = companyTypeRepository.findAll(specification, pageable);
        List<CompanyTypeResponse> list = converseCompanyType(page.getContent());
        Page<CompanyTypeResponse> responses = new PageImpl<>(list, page.getPageable(), page.getTotalElements());
        return Result.<Page<CompanyTypeResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<List<CompanyTypeResponse>> findAll() {
        List<CompanyType> all = companyTypeRepository.findAllByDeleteIsFalseAndAvailableIsTrueAndParentIsNull();
        List<CompanyTypeResponse> responses = converseCompanyType(all);
        return Result.<List<CompanyTypeResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<List<CompanyTypeDetailResponse>> findAllKind(String id) {
        List<CompanyType> all = companyTypeRepository.findAllByParent_IdAndDeleteIsFalseAndAvailableIsTrue(id);
        List<CompanyTypeDetailResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            CompanyTypeDetailResponse response = new CompanyTypeDetailResponse();
            BeanUtils.copyProperties(temp, response);
            result.add(response);
        });
        return Result.<List<CompanyTypeDetailResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<CompanyTypeDetailResponse>> findAllGeneral() {
        List<CompanyType> all = companyTypeRepository.findAllByDeleteIsFalseAndAvailableIsTrueAndParentIsNull();
        List<CompanyTypeDetailResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            CompanyTypeDetailResponse response = new CompanyTypeDetailResponse();
            BeanUtils.copyProperties(temp, response);
            result.add(response);
        });
        return Result.<List<CompanyTypeDetailResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<String> addGeneral(AddGeneralRequest request) {
        CompanyType type = new CompanyType();
        BeanUtils.copyProperties(request, type);
        type.setDelete(false);
        type.setAvailable(true);
        companyTypeRepository.save(type);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> addKind(AddKindTypeRequest request) {
        Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getId());
        if(!byId.isPresent()){
            throw new NormalException("大类不存在");
        }
        CompanyType type = byId.get();
        CompanyType kind = new CompanyType();
        kind.setName(request.getName());
        kind.setDelete(false);
        kind.setAvailable(true);
        kind.setParent(type);
        companyTypeRepository.save(kind);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editGeneral(EditTypeRequest request) {
        Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getId());
        if(!byId.isPresent()){
            throw new NormalException("类型不存在");
        }
        CompanyType type = byId.get();
        type.setName(request.getName());
        companyTypeRepository.save(type);
        return Result.<String>builder().success().data("编辑成功").build();
    }


    @Override
    public Result<String> deleteGeneral(String id) {
        Optional<CompanyType> byId = companyTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(byId.isPresent()){
            CompanyType type = byId.get();
            if(type.getParent() == null){
                //删除大类，先删除大类下的小类
                Set<CompanyType> children = type.getChildren();
                children.forEach(temp -> {
                    temp.setDelete(true);
                    companyTypeRepository.save(temp);
                });
            }
            type.setDelete(true);
            companyTypeRepository.save(type);
        }
        return Result.<String>builder().success().data("删除成功").build();
    }

    private List<CompanyTypeResponse> converseCompanyType(List<CompanyType> list){
        List<CompanyTypeResponse> result = Lists.newArrayList();
        list.forEach(temp -> {
            CompanyTypeResponse response = new CompanyTypeResponse();
            BeanUtils.copyProperties(temp, response);
            List<CompanyTypeDetailResponse> kind = Lists.newArrayList();
            temp.getChildren().forEach(children -> {
              if(!children.getDelete()){
                  CompanyTypeDetailResponse kindResponse = new CompanyTypeDetailResponse();
                  BeanUtils.copyProperties(children, kindResponse);
                  kind.add(kindResponse);
              }
            });
            response.setKind(kind);
            result.add(response);
        });
        return result;
    }
}

package com.parkinfo.service.archiveInfo.impl;

import com.parkinfo.common.Result;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.PolicyPaperResponse;
import com.parkinfo.service.archiveInfo.IPolicyPaperService;
import org.apache.commons.lang3.StringUtils;
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
public class PolicyPaperServiceImpl implements IPolicyPaperService {

    @Autowired
    private ArchiveInfoRepository policyPaperRepository;

    @Override
    public Result<Page<PolicyPaperResponse>> search(QueryArchiveInfoRequest request) {
        PolicyPaperResponse policyPaper = new PolicyPaperResponse();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<PolicyPaperResponse> specification = new Specification<PolicyPaperResponse>() {
            @Override
            public Predicate toPredicate(Root<PolicyPaperResponse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(request.getStartTime() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getStartTime()));  //大于等于开始时间
                }
                if(request.getEndTime() != null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getEndTime())); //小于等于结束时间
                }
                if(StringUtils.isNotBlank(request.getFileName())){
                    predicates.add(cb.like(root.get("fileName").as(String.class), "%"+request.getFileName()+"%"));  //根据文件名模糊查询
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<PolicyPaperResponse> all = policyPaperRepository.findAll(specification, pageable);
        return Result.<Page<PolicyPaperResponse>>builder().success().data(all).build();
    }

    @Override
    public Result<List<PolicyPaperResponse>> findAll(String policyType) {
        List<PolicyPaperResponse> all = policyPaperRepository.findAllByPolicyType(policyType);
        return Result.<List<PolicyPaperResponse>>builder().success().data(all).build();
    }

    @Override
    public Result<PolicyPaperResponse> findById(String id) {
        Optional<PolicyPaperResponse> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        return Result.<PolicyPaperResponse>builder().success().data(byId.get()).build();
    }

    @Override
    public Result<String> deletePolicyPaper(String id) {
        Optional<PolicyPaperResponse> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaperRepository.delete(byId.get());
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> addPolicyPaper(PolicyPaperResponse policyPaper) {
        //TODO
        policyPaperRepository.save(policyPaper);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editPolicyPaper(String id, PolicyPaperResponse policyPaper) {
        Optional<PolicyPaperResponse> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaper.setId(id);
        policyPaperRepository.save(policyPaper);
        return Result.<String>builder().success().data("编辑成功").build();
    }
}

package com.parkinfo.service.archiveInfo.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.PolicyPaper;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.PolicyPaperRepository;
import com.parkinfo.request.archiveInfo.QueryPolicyPaperRequest;
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
    private PolicyPaperRepository policyPaperRepository;

    @Override
    public Result<Page<PolicyPaper>> search(QueryPolicyPaperRequest request) {
        PolicyPaper policyPaper = new PolicyPaper();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<PolicyPaper> specification = new Specification<PolicyPaper>() {
            @Override
            public Predicate toPredicate(Root<PolicyPaper> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(request.getPolicyType())){
                    predicates.add(cb.equal(root.get("policyType").as(String.class), request.getPolicyType())); //根据政策文件类型
                }
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
        Page<PolicyPaper> all = policyPaperRepository.findAll(specification, pageable);
        return Result.<Page<PolicyPaper>>builder().success().data(all).build();
    }

    @Override
    public Result<List<PolicyPaper>> findAll(String policyType) {
        List<PolicyPaper> all = policyPaperRepository.findAllByPolicyType(policyType);
        return Result.<List<PolicyPaper>>builder().success().data(all).build();
    }

    @Override
    public Result<PolicyPaper> findById(String id) {
        Optional<PolicyPaper> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        return Result.<PolicyPaper>builder().success().data(byId.get()).build();
    }

    @Override
    public Result<String> deletePolicyPaper(String id) {
        Optional<PolicyPaper> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaperRepository.delete(byId.get());
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> addPolicyPaper(PolicyPaper policyPaper) {
        //TODO
        policyPaperRepository.save(policyPaper);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editPolicyPaper(String id, PolicyPaper policyPaper) {
        Optional<PolicyPaper> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaper.setId(id);
        policyPaperRepository.save(policyPaper);
        return Result.<String>builder().success().data("编辑成功").build();
    }
}

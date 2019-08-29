package com.parkinfo.service.archiveInfo.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.DeclarePaper;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.DeclarePaperRepository;
import com.parkinfo.request.archiveInfo.QueryDeclarePaperRequest;
import com.parkinfo.service.archiveInfo.IDeclarePaperService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class DeclarePaperServiceImpl implements IDeclarePaperService {

    @Autowired
    private DeclarePaperRepository declarePaperRepository;

    @Override
    public Result<Page<DeclarePaper>> search(QueryDeclarePaperRequest request) {
        DeclarePaper declarePaper = new DeclarePaper();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<DeclarePaper> specification = new Specification<DeclarePaper>() {
            @Override
            public Predicate toPredicate(Root<DeclarePaper> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(request.getDeclareType())){
                    predicates.add(cb.equal(root.get("declareType").as(String.class), request.getDeclareType()));   //根据申报材料类型
                }
                if(StringUtils.isNotBlank(request.getProjectName())){
                    predicates.add(cb.like(root.get("projectName").as(String.class), request.getProjectName()));    //根据项目名
                }
                if(StringUtils.isNotBlank(request.getFirmName())){
                    predicates.add(cb.like(root.get("firmName").as(String.class), request.getFirmName()));  //根据公司名
                }
                if(StringUtils.isNotBlank(request.getStatus())){
                    predicates.add(cb.like(root.get("status").as(String.class), request.getStatus()));  //根据状态
                }
                if(request.getStartTime() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startTime").as(Date.class), request.getStartTime()));  //TODO
                }
                if(request.getEndTime() != null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("endTime").as(Date.class), request.getStartTime()));  //TODO
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<DeclarePaper> all = declarePaperRepository.findAll(specification, pageable);
        return Result.<Page<DeclarePaper>>builder().success().data(all).build();
    }

    @Override
    public Result<List<DeclarePaper>> findAll(String declareType) {
        List<DeclarePaper> allByDeclareType = declarePaperRepository.findAllByDeclareType(declareType);
        return Result.<List<DeclarePaper>>builder().success().data(allByDeclareType).build();
    }

    @Override
    public Result<DeclarePaper> findById(String id) {
        Optional<DeclarePaper> byId = declarePaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        return Result.<DeclarePaper>builder().success().data(byId.get()).build();
    }

    @Override
    public Result<String> addDeclarePaper(DeclarePaper policyPaper) {
        //TODO
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<String> editDeclarePaper(String id, DeclarePaper declarePaper) {
        Optional<DeclarePaper> byId = declarePaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        declarePaper.setId(id);
        declarePaperRepository.save(declarePaper);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<String> deleteDeclarePaper(String id) {
        Optional<DeclarePaper> byId = declarePaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        DeclarePaper declarePaper = byId.get();
        declarePaperRepository.delete(declarePaper);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> changeStatus(String id) {
        Optional<DeclarePaper> byId = declarePaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        DeclarePaper declarePaper = byId.get();
        //TODO
        return null;
    }
}

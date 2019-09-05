package com.parkinfo.service.archiveInfo.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
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
public class ArchiveInfoServiceImpl implements IArchiveInfoService {

    @Autowired
    private ArchiveInfoRepository policyPaperRepository;

    @Override
    public Result<Page<ArchiveInfo>> search(QueryArchiveInfoRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<ArchiveInfo> specification = new Specification<ArchiveInfo>() {
            @Override
            public Predicate toPredicate(Root<ArchiveInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(request.getGeneral() != null){
                    predicates.add(cb.like(root.get("general").as(String.class), "%"+request.getGeneral()+"%")); //文件大类
                }
                if(request.getKind() != null){
                    predicates.add(cb.like(root.get("kind").as(String.class), "%"+request.getKind()+"%")); //文件种类
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
                if(request.getGradenName() != null){
                    predicates.add(cb.like(root.get("gradenName").as(String.class), "%"+request.getGradenName()+"%")); //园区名称
                }
                if(request.getRemark() != null){
                    predicates.add(cb.like(root.get("remark").as(String.class), "%"+request.getRemark()+"%")); //文档说明
                }
                if(request.getExternal() != null){
                    predicates.add(cb.equal(root.get("remark").as(Boolean.class), request.getExternal())); //是否对外
                }
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), false));  //没有被删除
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ArchiveInfo> all = policyPaperRepository.findAll(specification, pageable);
        return Result.<Page<ArchiveInfo>>builder().success().data(all).build();
    }

    @Override
    public Result<List<ArchiveInfo>> findAll() {
        List<ArchiveInfo> all = policyPaperRepository.findAllByDeleteIsFalse();
        return Result.<List<ArchiveInfo>>builder().success().data(all).build();
    }

    @Override
    public Result<ArchiveInfo> findById(String id) {
        Optional<ArchiveInfo> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        return Result.<ArchiveInfo>builder().success().data(byId.get()).build();
    }

    @Override
    public Result<String> deletePolicyPaper(String id) {
        Optional<ArchiveInfo> byId = policyPaperRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaperRepository.delete(byId.get());
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> addPolicyPaper(ArchiveInfo archiveInfo) {
        //todo 判断对外，存入学习资料
        archiveInfo.setDelete(false);
        //todo 关联园区
        String gradenId = "";
        archiveInfo.setDelete(false);
        archiveInfo.setAvailable(true);
        policyPaperRepository.save(archiveInfo);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editPolicyPaper(ArchiveInfo archiveInfo) {
        Optional<ArchiveInfo> byId = policyPaperRepository.findById(archiveInfo.getId());
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        policyPaperRepository.save(archiveInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }
}

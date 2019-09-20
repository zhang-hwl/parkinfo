package com.parkinfo.service.archiveInfo.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.request.archiveInfo.QueryLearnDataInfoRequest;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.response.archiveInfo.LearnDataInfoResponse;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoTypeService;
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
public class ArchiveInfoTypeService implements IArchiveInfoTypeService {

    @Autowired
    private ArchiveInfoTypeRepository archiveInfoTypeRepository;
    @Autowired
    private ArchiveInfoRepository archiveInfoRepository;

    @Override
    public Result<List<AllArchiveInfoTypeResponse>> findAll() {
        List<ArchiveInfoType> byAll = archiveInfoTypeRepository.findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();
        List<AllArchiveInfoTypeResponse> result = Lists.newArrayList();
        byAll.forEach(temp -> {
            AllArchiveInfoTypeResponse response = new AllArchiveInfoTypeResponse();
            BeanUtils.copyProperties(temp, response);
            List<ArchiveInfoTypeResponse> kindType = Lists.newArrayList();
            temp.getChildren().forEach(children -> {
                ArchiveInfoTypeResponse typeResponse = new ArchiveInfoTypeResponse();
                BeanUtils.copyProperties(children, typeResponse);
                kindType.add(typeResponse);
            });
            response.setKind(kindType);
            result.add(response);
        });
        return Result.<List<AllArchiveInfoTypeResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<Page<LearnDataInfoResponse>> search(QueryLearnDataInfoRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "uploadTime");
        Specification<ArchiveInfo> specification = new Specification<ArchiveInfo>() {
            @Override
            public Predicate toPredicate(Root<ArchiveInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                if(StringUtils.isNotBlank(request.getGeneral())){
                    predicates.add(cb.equal(root.get("generalId").as(String.class), request.getGeneral()));
                }
                if(StringUtils.isNotBlank(request.getKind())){
                    predicates.add(cb.equal(root.get("kind").get("id").as(String.class), request.getKind())); //文件种类
                }
                predicates.add(cb.equal(root.get("external").as(Boolean.class), true)); //对外
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), false));  //没有被删除
                predicates.add(cb.equal(root.get("available").as(Boolean.class), true));  //可用
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ArchiveInfo> all = archiveInfoRepository.findAll(specification, pageable);
        List<LearnDataInfoResponse> list = Lists.newArrayList();
        all.getContent().forEach(temp -> {
            LearnDataInfoResponse response = new LearnDataInfoResponse();
            BeanUtils.copyProperties(temp, response);
            response.setArchiveInfoType(temp.getKind());
            list.add(response);
        });
        Page<LearnDataInfoResponse> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<LearnDataInfoResponse>>builder().success().data(result).build();
    }
}

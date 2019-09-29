package com.parkinfo.service.homePage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.notice.SystemNotice;
import com.parkinfo.request.notice.QueryNoticeRequest;
import com.parkinfo.entity.notice.SystemNoticeEntity;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.homePage.SystemNoticeRepository;
import com.parkinfo.service.homePage.ISystemNoticeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    @Autowired
    private SystemNoticeRepository systemNoticeRepository;

    @Override
    public Result<String> addNotice(SystemNotice systemNotice) {
        SystemNoticeEntity entity = new SystemNoticeEntity();
        BeanUtils.copyProperties(systemNotice, entity);
        entity.setDelete(false);
        entity.setAvailable(true);
        systemNoticeRepository.save(entity);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<Page<SystemNotice>> findAll(QueryNoticeRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<SystemNoticeEntity> specification = new Specification<SystemNoticeEntity>() {
            @Override
            public Predicate toPredicate(Root<SystemNoticeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(request.getTitle())){
                    predicates.add(cb.like(root.get("title").as(String.class), "%"+request.getTitle()+"%"));
                }
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
                predicates.add(cb.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<SystemNoticeEntity> all = systemNoticeRepository.findAll(specification, pageable);
        List<SystemNotice> list = Lists.newArrayList();
        all.getContent().forEach(temp -> {
            SystemNotice systemNotice = new SystemNotice();
            BeanUtils.copyProperties(temp, systemNotice);
            systemNotice.setUploadTime(temp.getCreateTime());
            list.add(systemNotice);
        });
        Page<SystemNotice> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<SystemNotice>>builder().success().data(result).build();
    }

    @Override
    public Result<String> deleteById(String id) {
        Optional<SystemNoticeEntity> byId = systemNoticeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        SystemNoticeEntity entity = byId.get();
        entity.setDelete(true);
        systemNoticeRepository.save(entity);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<List<SystemNotice>> findByLimit(Integer count) {
        List<SystemNoticeEntity> byLimit = systemNoticeRepository.findByLimit(count);
        List<SystemNotice> result = Lists.newArrayList();
        byLimit.forEach(temp -> {
            SystemNotice notice = new SystemNotice();
            BeanUtils.copyProperties(temp, notice);
            notice.setUploadTime(temp.getCreateTime());
            result.add(notice);
        });
        return Result.<List<SystemNotice>>builder().success().data(result).build();
    }
}

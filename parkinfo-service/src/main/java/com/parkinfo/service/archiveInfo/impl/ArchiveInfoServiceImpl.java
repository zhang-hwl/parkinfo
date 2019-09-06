package com.parkinfo.service.archiveInfo.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveComment;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.repository.archiveInfo.ArchiveReadRecordRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.archiveInfo.AddArchiveInfoRequest;
import com.parkinfo.request.archiveInfo.ArchiveReadRecordRequest;
import com.parkinfo.request.archiveInfo.ReadRecordRequest;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArchiveInfoServiceImpl implements IArchiveInfoService {

    @Autowired
    private ArchiveInfoRepository archiveInfoRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkInfoRepository parkInfoRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;
    @Autowired
    private ArchiveReadRecordRepository archiveReadRecordRepository;

    @Override
    public Result<Page<ArchiveInfoResponse>> search(QueryArchiveInfoRequest request) {
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
                if(StringUtils.isNotBlank(request.getParkId())){
                    predicates.add(cb.equal(root.get("parkInfo.id").as(String.class), request.getParkId())); //园区ID
                }
                if(request.getStartTime() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getStartTime()));  //大于等于开始时间
                }
                if(request.getEndTime() != null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("uploadTime").as(Date.class), request.getEndTime())); //小于等于结束时间
                }
                if(StringUtils.isNotBlank(request.getRemark())){
                    predicates.add(cb.like(root.get("remark").as(String.class), "%"+request.getRemark()+"%")); //文档说明
                }
                if(request.getExternal() != null){
                    predicates.add(cb.equal(root.get("remark").as(Boolean.class), request.getExternal())); //是否对外
                }
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), false));  //没有被删除
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ArchiveInfo> all = archiveInfoRepository.findAll(specification, pageable);
        List<ArchiveInfoResponse> response = new ArrayList<>();
        all.getContent().forEach(temp->{
            ArchiveInfoResponse archiveInfoResponse = new ArchiveInfoCommentResponse();
            BeanUtils.copyProperties(temp, archiveInfoResponse);
            response.add(archiveInfoResponse);
        });
        Page<ArchiveInfoResponse> result = new PageImpl<>(response, all.getPageable(), all.getTotalElements());
        BeanUtils.copyProperties(all, result);
        return Result.<Page<ArchiveInfoResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<ArchiveInfoResponse>> findAll() {
        List<ArchiveInfo> all = archiveInfoRepository.findAllByDeleteIsFalse();
        List<ArchiveInfoResponse> result = new ArrayList<>();
        all.forEach(archiveInfo -> {
            ArchiveInfoResponse response = new ArchiveInfoResponse();
            BeanUtils.copyProperties(archiveInfo, response);
            result.add(response);
        });
        return Result.<List<ArchiveInfoResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<ArchiveInfoCommentResponse> findById(String id) {
        Optional<ArchiveInfo> byId = archiveInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfoCommentResponse result = new ArchiveInfoCommentResponse();
        BeanUtils.copyProperties(byId.get(), result);
        return Result.<ArchiveInfoCommentResponse>builder().success().data(result).build();
    }

    @Override
    public Result<String> deleteArchiveInfo(String id) {
        Optional<ArchiveInfo> byId = archiveInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfo archiveInfo = byId.get();
        archiveInfo.setDelete(true);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> addArchiveInfo(AddArchiveInfoRequest request) {
        ArchiveInfo archiveInfo = new ArchiveInfo();
        BeanUtils.copyProperties(request, archiveInfo);
        String parkId = tokenUtils.getLoginUserDTO().getCurrentParkId();
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        archiveInfo.setParkInfo(parkInfo);
        if(archiveInfo.getExternal() == true){
            //todo 对外，存入学习资料
        }
        archiveInfo.setDelete(false);
        archiveInfo.setAvailable(true);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editArchiveInfo(String id, AddArchiveInfoRequest request) {
        Optional<ArchiveInfo> byId = archiveInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfo archiveInfo = byId.get();
        BeanUtils.copyProperties(request, archiveInfo);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<String> addReadRecord(String id) {
        String userId = tokenUtils.getLoginUser().getId();
        Optional<ArchiveInfo> byIdAndDeleteIsFalse = archiveInfoRepository.findByIdAndDeleteIsFalse(id);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfo archiveInfo = byIdAndDeleteIsFalse.get();
        List<ArchiveReadRecord> archiveReadRecords = archiveInfo.getArchiveReadRecords();
        Optional<ParkUser> parkUser = parkUserRepository.findByIdAndDeleteIsFalse(userId);
        if(!parkUser.isPresent()){
            throw new NormalException("用户不存在");
        }
        ParkUser user = parkUser.get();
        ArchiveReadRecord archiveReadRecord = new ArchiveReadRecord();
        archiveReadRecord.setDelete(false);
        archiveReadRecord.setAvailable(true);
        archiveReadRecord.setAvatar(user.getAvatar());
        archiveReadRecord.setNickname(user.getNickname());
        archiveReadRecord.setBookName(archiveInfo.getFileName());
        archiveReadRecord.setReadDate(new Date());
        archiveReadRecord.setFileId(id);
        archiveReadRecords.add(archiveReadRecord);
        archiveInfo.setArchiveReadRecords(archiveReadRecords);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("新增阅读记录成功").build();
    }

    @Override
    public Result<String> addComment(ReadRecordRequest request) {
        String userId = tokenUtils.getLoginUser().getId();
        Optional<ArchiveInfo> byIdAndDeleteIsFalse = archiveInfoRepository.findByIdAndDeleteIsFalse(request.getArchiveId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfo archiveInfo = byIdAndDeleteIsFalse.get();
        List<ArchiveComment> archiveComments = archiveInfo.getArchiveComments();
        Optional<ParkUser> parkUser = parkUserRepository.findByIdAndDeleteIsFalse(userId);
        if(!parkUser.isPresent()){
            throw new NormalException("用户不存在");
        }
        ParkUser user = parkUser.get();
        ArchiveComment archiveComment = new ArchiveComment();
        archiveComment.setAvailable(true);
        archiveComment.setDelete(false);
        archiveComment.setComment(request.getRemark());
        archiveComment.setNickname(user.getNickname());
        archiveComment.setAvatar(user.getAvatar());
        archiveComments.add(archiveComment);
        archiveInfo.setArchiveComments(archiveComments);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("评论成功").build();
    }

    @Override
    public Result<Page<ArchiveReadRecord>> findReadRecord(ArchiveReadRecordRequest request) {
        Optional<ArchiveInfo> byIdAndDeleteIsFalse = archiveInfoRepository.findByIdAndDeleteIsFalse(request.getId());
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("文件不存在");
        }
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.DEFAULT_DIRECTION.DESC, "readDate");
        Specification<ArchiveReadRecord> specification = new Specification<ArchiveReadRecord>() {
            @Override
            public Predicate toPredicate(Root<ArchiveReadRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(cb.equal(root.get("fileId").as(String.class), request.getId()));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ArchiveReadRecord> all = archiveReadRecordRepository.findAll(specification, pageable);
        return Result.<Page<ArchiveReadRecord>>builder().success().data(all).build();
    }

}

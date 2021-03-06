package com.parkinfo.service.archiveInfo.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.archiveInfo.ArchiveComment;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkRole;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ConvertStatus;
import com.parkinfo.enums.DefaultEnum;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveCommentRepository;
import com.parkinfo.repository.archiveInfo.ArchiveInfoRepository;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.repository.archiveInfo.ArchiveReadRecordRepository;
import com.parkinfo.repository.parkService.LearningDataRepository;
import com.parkinfo.repository.userConfig.ParkInfoRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.archiveInfo.*;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.sender.OfficeFileTransferTaskSender;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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
    @Autowired
    private ArchiveCommentRepository archiveCommentRepository;
    @Autowired
    private ArchiveInfoTypeRepository archiveInfoTypeRepository;
    @Autowired
    private LearningDataRepository learningDataRepository;
    @Autowired
    private OfficeFileTransferTaskSender sender;

    @Override
    public Result<Page<ArchiveInfoResponse>> search(QueryArchiveInfoRequest request) {
        return Result.<Page<ArchiveInfoResponse>>builder().success().data(null).build();
    }

    @Override
    public Result<ArchiveInfoCommentResponse> findById(String id) {
        Optional<ArchiveInfo> byId = archiveInfoRepository.findById(id);
        if(!byId.isPresent()){
            throw new NormalException("文件不存在");
        }
        ArchiveInfoCommentResponse result = new ArchiveInfoCommentResponse();
        BeanUtils.copyProperties(byId.get(), result);
        result.setPdfAddress(byId.get().getPdfAddress());
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
        if(StringUtils.isBlank(request.getFileAddress())){
            throw new NormalException("请上传附件");
        }
        ParkUserDTO loginUserDTO = tokenUtils.getLoginUserDTO();
        if(loginUserDTO == null){
            throw new NormalException("token过期或不存在");
        }
        ParkUser parkUser = parkUserRepository.findByIdAndDeleteIsFalse(loginUserDTO.getId()).get();
        String parkId = loginUserDTO.getCurrentParkId();
        String fileAddress = request.getFileAddress();
        ArchiveInfo archiveInfo = new ArchiveInfo();
        BeanUtils.copyProperties(request, archiveInfo);
        if(!fileAddress.endsWith("docx") && !fileAddress.endsWith("doc") && !fileAddress.endsWith("ppt") && !fileAddress.endsWith("pptx") && !fileAddress.endsWith("xlsx") && !fileAddress.endsWith("xls") && !fileAddress.endsWith("pdf")){
            archiveInfo.setConvertStatus(ConvertStatus.FAILED);
            archiveInfo.setPdfAddress(request.getFileAddress());
        }
        else if(fileAddress.endsWith("pdf")){
            archiveInfo.setConvertStatus(ConvertStatus.SUCCESS);
            archiveInfo.setPdfAddress(request.getFileAddress());
        }
        else{
            archiveInfo.setConvertStatus(ConvertStatus.WAITING);
        }
        Optional<ParkInfo> byIdAndDeleteIsFalse = parkInfoRepository.findByIdAndDeleteIsFalse(parkId);
        if(!byIdAndDeleteIsFalse.isPresent()){
            throw new NormalException("园区不存在");
        }
        ParkInfo parkInfo = byIdAndDeleteIsFalse.get();
        archiveInfo.setParkInfo(parkInfo);
        Optional<ArchiveInfoType> byBigType = archiveInfoTypeRepository.findByTypeAndDeleteIsFalseAndAvailableIsTrue(request.getGeneral());
        Optional<ArchiveInfoType> bySmallType = archiveInfoTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getKind());
        if(!byBigType.isPresent() || !bySmallType.isPresent()){
            throw new NormalException("类型不存在");
        }
        archiveInfo.setGeneralId(byBigType.get().getId());
        archiveInfo.setKind(bySmallType.get());
        archiveInfo.setHeir(parkUser.getNickname());
        archiveInfo.setUploadTime(new Date());
        archiveInfo.setDelete(false);
        archiveInfo.setAvailable(true);
        archiveInfo.setFileType(request.getFileType().toLowerCase());
        ArchiveInfo save = archiveInfoRepository.save(archiveInfo);
        if(fileAddress.endsWith("docx") || fileAddress.endsWith("doc") || fileAddress.endsWith("ppt") || fileAddress.endsWith("pptx") || fileAddress.endsWith("xlsx") || fileAddress.endsWith("xls")){
            sender.send(save.getId());
        }
//        if(request.getExternal()){
//            LearningData learningData = new LearningData();
//            BeanUtils.copyProperties(save, learningData);
//            learningData.setFilePath(save.getFileAddress());
//            learningData.setDescription(save.getRemark());
//            learningDataRepository.save(learningData);
//        }
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
        if(StringUtils.isNotBlank(request.getKind())){
            Optional<ArchiveInfoType> byTypeId = archiveInfoTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getKind());
            if(!byTypeId.isPresent()){
                throw new NormalException("类型不存在");
            }
            ArchiveInfoType archiveInfoType = byTypeId.get();
            archiveInfo.setKind(archiveInfoType);
        }
        archiveInfoRepository.save(archiveInfo);
        if(!archiveInfo.getFileAddress().equals(request.getFileAddress())){
            sender.send(archiveInfo.getId());
        }
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
        archiveReadRecordRepository.save(archiveReadRecord);
        archiveReadRecords.add(archiveReadRecord);
        archiveInfo.setArchiveReadRecords(archiveReadRecords);
        archiveInfoRepository.save(archiveInfo);
        return Result.<String>builder().success().data("新增阅读记录成功").build();
    }

    @Override
    public Result<String> addComment(ArchiveCommentRequest request) {
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
        archiveCommentRepository.save(archiveComment);
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

    @Override
    public Result<ArchiveInfoType> findType(String id) {
        Optional<ArchiveInfoType> byIdAndDeleteIsFalseAndAvailableIsTrue = archiveInfoTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byIdAndDeleteIsFalseAndAvailableIsTrue.isPresent()){
            throw new NormalException("文件类型不存在");
        }
        ArchiveInfoType archiveInfoType = byIdAndDeleteIsFalseAndAvailableIsTrue.get();
        return Result.<ArchiveInfoType>builder().success().data(archiveInfoType).build();
    }

    @Override
    public Result<List<ParkInfoResponse>> findAllPark() {
        List<ParkInfo> byAll = parkInfoRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<ParkInfoResponse> result = Lists.newArrayList();
        byAll.forEach(temp -> {
            if(!temp.getId().equals(DefaultEnum.CEO_PARK.getDefaultValue())){
                ParkInfoResponse response = new ParkInfoResponse();
                BeanUtils.copyProperties(temp, response);
                result.add(response);
            }
        });
        return Result.<List<ParkInfoResponse>>builder().success().data(result).build();
    }

    public Result<List<ArchiveInfoTypeResponse>> findAllType(String general){
        Optional<ArchiveInfoType> byType = archiveInfoTypeRepository.findByTypeAndDeleteIsFalseAndAvailableIsTrue(general);
        if(!byType.isPresent()){
            throw new NormalException("类型不存在");
        }
        Set<ArchiveInfoType> children = byType.get().getChildren();
        List<ArchiveInfoTypeResponse> result = Lists.newArrayList();
        children.forEach(temp -> {
            ArchiveInfoTypeResponse response = new ArchiveInfoTypeResponse();
            BeanUtils.copyProperties(temp, response);
            result.add(response);
        });
        return Result.<List<ArchiveInfoTypeResponse>>builder().success().data(result).build();
    }


}

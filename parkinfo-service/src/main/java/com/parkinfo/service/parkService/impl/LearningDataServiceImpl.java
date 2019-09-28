package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveComment;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.learningData.LearnDataComment;
import com.parkinfo.entity.parkService.learningData.LearnDataType;
import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.repository.parkService.LearnDataCommentRepository;
import com.parkinfo.repository.parkService.LearnDataTypeRepository;
import com.parkinfo.repository.parkService.LearningDataRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.archiveInfo.ArchiveCommentRequest;
import com.parkinfo.request.archiveInfo.ArchiveReadRecordRequest;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.request.parkService.learningData.LearnDataTypeRequest;
import com.parkinfo.response.parkService.LearnDataCommentResponse;
import com.parkinfo.response.parkService.LearnDataKindResponse;
import com.parkinfo.response.parkService.LearnDataTypeResponse;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.request.parkService.learningData.SearchLearningDateRequest;
import com.parkinfo.service.parkService.ILearningDataService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LearningDataServiceImpl implements ILearningDataService {
    @Autowired
    private LearningDataRepository learningDataRepository;
    @Autowired
    private ParkUserRepository parkUserRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private LearnDataTypeRepository learnDataTypeRepository;
    @Autowired
    private LearnDataCommentRepository learnDataCommentRepository;

    @Override
    public Result<Page<LearningDateResponse>> searchLearningData(SearchLearningDateRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        Specification<LearningData> specification = (Specification<LearningData>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateLists = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getFileName())){
                predicateLists.add(criteriaBuilder.like(root.get("fileName").as(String.class),"%"+request.getFileName()+"%"));
            }
            if (StringUtils.isNotBlank(request.getSmallTypeId())){
                predicateLists.add(criteriaBuilder.equal(root.get("learnDataType").as(LearnDataType.class),this.checkLearnDataType(request.getSmallTypeId())));
            }else if (StringUtils.isNotBlank(request.getBigTypeId())){
                LearnDataType type = this.checkLearnDataType(request.getBigTypeId());
                CriteriaBuilder.In<LearnDataType> in = criteriaBuilder.in(root.get("learnDataType").as(LearnDataType.class));
                Set<LearnDataType> children = type.getChildren();
                children.forEach(in::value);
                predicateLists.add(in);
            }
            predicateLists.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicateLists.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicateLists.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
            return criteriaBuilder.and(predicateLists.toArray(new Predicate[0]));
        };
        Page<LearningData> learningDataPage = learningDataRepository.findAll(specification,pageable);
        Page<LearningDateResponse> responses = this.convertLearningDateResponsePage(learningDataPage);
        return Result.<Page<LearningDateResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<String> addLearningData(AddLearningDataRequest request) {
        LearningData learningData = new LearningData();
        BeanUtils.copyProperties(request,learningData);
        learningData.setDelete(Boolean.FALSE);
        learningData.setAvailable(Boolean.TRUE);
        if (StringUtils.isNotBlank(request.getTypeId())){
            LearnDataType type = this.checkLearnDataType(request.getTypeId());
            learningData.setLearnDataType(type);
        }
        learningData.setParkInfo(tokenUtils.getCurrentParkInfo());
        learningDataRepository.save(learningData);
        return Result.<String>builder().success().message("新增成功").build();
    }

    @Override
    public Result<String> deleteLearningData(String id) {
        LearningData learningData = this.checkLearningData(id);
        learningData.setDelete(Boolean.TRUE);
        learningDataRepository.save(learningData);
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result<LearnDataCommentResponse> detailLearningData(String id) {
        LearningData learningData = checkLearningData(id);
        LearnDataCommentResponse response = new LearnDataCommentResponse();
        BeanUtils.copyProperties(learningData, response);
        return Result.<LearnDataCommentResponse>builder().success().data(response).build();
    }

    @Override
    public Result<String> editLearningData(EditLearningDataRequest request) {
        LearningData learningData = this.checkLearningData(request.getId());
        if (StringUtils.isNotBlank(request.getTypeId())){
            LearnDataType type = this.checkLearnDataType(request.getTypeId());
            learningData.setLearnDataType(type);
        }
        learningData.setFileName(request.getFileName());
        learningData.setDescription(request.getDescription());
        learningData.setFilePath(request.getFilePath());
        learningData.setFileType(request.getFileType());
        learningDataRepository.save(learningData);
        return Result.<String>builder().success().message("编辑成功").build();
    }

    private LearningData checkLearningData(String id) {
        Optional<LearningData> learningDataOptional = learningDataRepository.findById(id);
        if (!learningDataOptional.isPresent()){
            throw new NormalException("学习资料不存在");
        }
        return learningDataOptional.get();
    }

    private LearnDataType checkLearnDataType(String typeId) {
        Optional<LearnDataType> byId = learnDataTypeRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(typeId);
        if (!byId.isPresent()){
            throw new NormalException("书籍分类不存在");
        }
        return byId.get();
    }

    private Page<LearningDateResponse> convertLearningDateResponsePage(Page<LearningData> learningDataPage) {
        List<LearningDateResponse> responseList = Lists.newArrayList();
        learningDataPage.getContent().forEach(learningData -> {
            LearningDateResponse response = new LearningDateResponse();
            BeanUtils.copyProperties(learningData,response);
            response.setLearnDataType(learningData.getLearnDataType());
            responseList.add(response);
        });
        return new PageImpl<>(responseList,learningDataPage.getPageable(),learningDataPage.getTotalElements());
    }

    @Override
    public Result<List<LearnDataTypeResponse>> findAllType() {
        List<LearnDataType> all = learnDataTypeRepository.findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();
        List<LearnDataTypeResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            LearnDataTypeResponse response = new LearnDataTypeResponse();
            List<LearnDataKindResponse> list = Lists.newArrayList();
            Set<LearnDataType> children = temp.getChildren();
            children.forEach(kind -> {
                if(!kind.getDelete()){
                    LearnDataKindResponse kindResponse = new LearnDataKindResponse();
                    BeanUtils.copyProperties(kind, kindResponse);
                    list.add(kindResponse);
                }
            });
            BeanUtils.copyProperties(temp, response);
            response.setKind(list);
            result.add(response);
        });
        return Result.<List<LearnDataTypeResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<Page<LearnDataTypeResponse>> searchType(com.parkinfo.request.base.PageRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<LearnDataType> specification = new Specification<LearnDataType>() {
            @Override
            public Predicate toPredicate(Root<LearnDataType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(cb.isNull(root.get("parent").as(LearnDataType.class)));
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
                predicates.add(cb.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<LearnDataType> all = learnDataTypeRepository.findAll(specification, pageable);
        List<LearnDataTypeResponse> list = Lists.newArrayList();
        all.getContent().forEach(temp -> {
            LearnDataTypeResponse response = new LearnDataTypeResponse();
            BeanUtils.copyProperties(temp, response);
            List<LearnDataKindResponse> kind = Lists.newArrayList();
            temp.getChildren().forEach(children -> {
               if(!children.getDelete()){
                   LearnDataKindResponse childrenResponse = new LearnDataKindResponse();
                   BeanUtils.copyProperties(children, childrenResponse);
                   kind.add(childrenResponse);
               }
            });
            response.setKind(kind);
            list.add(response);
        });
        Page<LearnDataTypeResponse> result = new PageImpl<>(list, all.getPageable(), all.getTotalElements());
        return Result.<Page<LearnDataTypeResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<String> addType(LearnDataTypeRequest request) {
        LearnDataType learnDataType = new LearnDataType();
        learnDataType.setDelete(false);
        learnDataType.setAvailable(true);
        if(StringUtils.isBlank(request.getKindName())){
            //新增大类
            learnDataType.setType(request.getGeneralName());
        }
        else{
            LearnDataType general = checkLearnDataType(request.getGeneralId());
            learnDataType.setParent(general);
            learnDataType.setType(request.getKindName());
        }
        learnDataTypeRepository.save(learnDataType);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> editType(LearnDataTypeRequest request) {
        LearnDataType learnDataType = new LearnDataType();
        learnDataType.setDelete(false);
        learnDataType.setAvailable(true);
        if(StringUtils.isBlank(request.getKindId())){
            //编辑大类
            LearnDataType temp = checkLearnDataType(request.getGeneralId());
            BeanUtils.copyProperties(temp, learnDataType);
            learnDataType.setType(request.getGeneralName());
        }
        else{
            LearnDataType general = checkLearnDataType(request.getGeneralId());
            LearnDataType kind = checkLearnDataType(request.getKindId());
            BeanUtils.copyProperties(kind, learnDataType);
            learnDataType.setParent(general);
            learnDataType.setType(request.getKindName());
        }
        learnDataTypeRepository.save(learnDataType);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<String> deleteType(String id) {
        LearnDataType learnDataType = checkLearnDataType(id);
        learnDataType.setDelete(true);
        learnDataTypeRepository.save(learnDataType);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> addComment(ArchiveCommentRequest request) {
        String userId = tokenUtils.getLoginUser().getId();
        LearningData learningData = checkLearningData(request.getArchiveId());
        List<LearnDataComment> learnDataComments = learningData.getLearnDataComments();
        Optional<ParkUser> parkUser = parkUserRepository.findByIdAndDeleteIsFalse(userId);
        if(!parkUser.isPresent()){
            throw new NormalException("用户不存在");
        }
        ParkUser user = parkUser.get();
        LearnDataComment learnDataComment = new LearnDataComment();
        learnDataComment.setAvailable(true);
        learnDataComment.setDelete(false);
        learnDataComment.setComment(request.getRemark());
        learnDataComment.setNickname(user.getNickname());
        learnDataComment.setAvatar(user.getAvatar());
        learnDataComments.add(learnDataComment);
        learnDataCommentRepository.save(learnDataComment);
        learningData.setLearnDataComments(learnDataComments);
        learningDataRepository.save(learningData);
        return Result.<String>builder().success().data("评论成功").build();
    }

    @Override
    public Result<String> addReadRecord(String id) {
        return null;
    }

//    @Override
//    public Result<Page<LearnReadRecord>> findReadRecord(ArchiveReadRecordRequest request) {
//        return null;
//    }
}

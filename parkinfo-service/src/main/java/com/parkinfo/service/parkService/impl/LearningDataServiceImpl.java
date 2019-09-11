package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.repository.parkService.LearningDataRepository;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.request.parkService.learningData.SearchLearningDateRequest;
import com.parkinfo.service.parkService.ILearningDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningDataServiceImpl implements ILearningDataService {
    @Autowired
    private LearningDataRepository learningDataRepository;
    @Autowired
    private ArchiveInfoTypeRepository archiveInfoTypeRepository;

    @Override
    public Result<Page<LearningDateResponse>> searchLearningData(SearchLearningDateRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        LearningData exampleData = new LearningData();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(request.getFileName())){
            exampleData.setFileName(request.getFileName());
            exampleMatcher = exampleMatcher.withMatcher("fileName",ExampleMatcher.GenericPropertyMatchers.contains());
        }
        exampleData.setDelete(Boolean.FALSE);
        exampleData.setAvailable(Boolean.TRUE);
        Example<LearningData> example = Example.of(exampleData, exampleMatcher);
        Page<LearningData> learningDataPage = learningDataRepository.findAll(example, pageable);
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
            ArchiveInfoType type = this.checkArchiveInfoType(request.getTypeId());
            learningData.setArchiveInfoType(type);
        }
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
    public Result<String> editLearningData(EditLearningDataRequest request) {
        LearningData learningData = this.checkLearningData(request.getId());
        if (StringUtils.isNotBlank(request.getTypeId())){
            ArchiveInfoType type = this.checkArchiveInfoType(request.getTypeId());
            learningData.setArchiveInfoType(type);
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

    private ArchiveInfoType checkArchiveInfoType(String typeId) {
        Optional<ArchiveInfoType> archiveInfoTypeOptional = archiveInfoTypeRepository.findById(typeId);
        if (!archiveInfoTypeOptional.isPresent()){
            throw new NormalException("书籍分类不存在");
        }
        return archiveInfoTypeOptional.get();
    }

    private Page<LearningDateResponse> convertLearningDateResponsePage(Page<LearningData> learningDataPage) {
        List<LearningDateResponse> responseList = Lists.newArrayList();
        learningDataPage.getContent().forEach(learningData -> {
            LearningDateResponse response = new LearningDateResponse();
            BeanUtils.copyProperties(learningData,response);
            response.setArchiveInfoType(learningData.getArchiveInfoType());
            responseList.add(response);
        });
        return new PageImpl<>(responseList,learningDataPage.getPageable(),learningDataPage.getTotalElements());
    }
}

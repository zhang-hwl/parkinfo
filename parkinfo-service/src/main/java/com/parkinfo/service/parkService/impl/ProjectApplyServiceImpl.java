package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.parkService.activityApply.ActivityApply;
import com.parkinfo.entity.parkService.projectApply.ProjectApplyRecord;
import com.parkinfo.entity.parkService.projectApply.ProjectInfo;
import com.parkinfo.entity.parkService.projectApply.ProjectType;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ProjectApplyStatus;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.companyManage.CompanyDetailRepository;
import com.parkinfo.repository.parkService.ProjectApplyRecordRepository;
import com.parkinfo.repository.parkService.ProjectInfoRepository;
import com.parkinfo.repository.parkService.ProjectTypeRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.parkService.projectApply.AddProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.ChangeStatusRequest;
import com.parkinfo.request.parkService.projectApply.EditProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.SearchProjectInfoRequest;
import com.parkinfo.response.parkService.ActivityApplyResponse;
import com.parkinfo.response.parkService.ProjectApplyRecordResponse;
import com.parkinfo.response.parkService.ProjectApplyRecordTypeResponse;
import com.parkinfo.response.parkService.ProjectInfoResponse;
import com.parkinfo.service.parkService.IProjectApplyService;
import com.parkinfo.token.TokenUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectApplyServiceImpl implements IProjectApplyService {
    @Autowired
    private ProjectInfoRepository projectInfoRepository;
    @Autowired
    private ProjectApplyRecordRepository projectApplyRecordRepository;
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private CompanyDetailRepository companyDetailRepository;

    @Override
    public Result<String> addProjectInfo(AddProjectInfoRequest request) {
        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtils.copyProperties(request,projectInfo);
        projectInfo.setParkInfo(tokenUtils.getCurrentParkInfo());
        if (StringUtils.isNotBlank(request.getTypeId())){
            ProjectType projectType = this.checkProjectType(request.getTypeId());
            projectInfo.setProjectType(projectType);
        }
        projectInfo.setDelete(Boolean.FALSE);
        projectInfo.setAvailable(Boolean.TRUE);
        projectInfoRepository.save(projectInfo);
        return Result.<String>builder().success().message("新增项目成功").build();
    }

    @Override
    public Result<List<ProjectApplyRecordResponse>> searchCompany(String id) {
        ProjectInfo projectInfo = this.checkProjectInfo(id);
        List<ProjectApplyRecord> projectApplyRecordList = projectApplyRecordRepository.findByDeleteIsFalseAndProjectInfo_Id(projectInfo.getId());
        List<ProjectApplyRecordResponse> responses = this.convertProjectApplyRecordResponse(projectApplyRecordList);
        return Result.<List<ProjectApplyRecordResponse>>builder().success().data(responses).build();
    }

    @Override
    public Result<String> changeStatus(ChangeStatusRequest request) {
        ProjectApplyRecord applyRecord = this.checkProjectApplyRecord(request.getRecordId());
        applyRecord.setStatus(request.getStatus());
        projectApplyRecordRepository.save(applyRecord);
        return Result.<String>builder().success().message("修改状态成功").build();
    }

    @Override
    public Result<ProjectApplyRecordResponse> detailRecord(String recordId) {
        ProjectApplyRecord projectApplyRecord = checkProjectApplyRecord(recordId);
        ProjectApplyRecordResponse response = new ProjectApplyRecordResponse();
        BeanUtils.copyProperties(projectApplyRecord, response);
        response.setCompanyName(projectApplyRecord.getCompanyDetail().getCompanyName());
        response.setContacts(projectApplyRecord.getCompanyDetail().getLinkMan());
        response.setContactNumber(projectApplyRecord.getCompanyDetail().getPhone());
        return Result.<ProjectApplyRecordResponse>builder().success().data(response).build();
    }

    @Override
    public Result<String> addType(ProjectApplyRecordTypeResponse recordTypeResponse) {
        ProjectType projectType = new ProjectType();
        projectType.setDelete(false);
        projectType.setAvailable(true);
        projectType.setValue(recordTypeResponse.getType());
        projectTypeRepository.save(projectType);
        return Result.<String>builder().success().data("新增成功").build();
    }

    @Override
    public Result<String> deleteType(String id) {
        ProjectType projectType = checkProjectType(id);
        projectTypeRepository.delete(projectType);
        return Result.<String>builder().success().data("删除成功").build();
    }

    @Override
    public Result<String> editType(ProjectApplyRecordTypeResponse recordTypeResponse) {
        ProjectType projectType = checkProjectType(recordTypeResponse.getId());
        projectType.setValue(recordTypeResponse.getType());
        projectTypeRepository.save(projectType);
        return Result.<String>builder().success().data("编辑成功").build();
    }

    @Override
    public Result<List<ProjectApplyRecordTypeResponse>> findAllType() {
        List<ProjectType> all = projectTypeRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<ProjectApplyRecordTypeResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            ProjectApplyRecordTypeResponse response = new ProjectApplyRecordTypeResponse();
            BeanUtils.copyProperties(temp, response);
            response.setType(temp.getValue());
            result.add(response);
        });
        return Result.<List<ProjectApplyRecordTypeResponse>>builder().success().data(result).build();
    }

    @Override
    public Result<List<ProjectApplyRecordResponse>> searchMyApplyRecord() {
        ParkUserDTO loginUserDTO = tokenUtils.getLoginUserDTO();
        List<ProjectApplyRecord> projectApplyRecordList = projectApplyRecordRepository.findByDeleteIsFalseAndCompanyDetail_ParkUser_Id(loginUserDTO.getId());
        List<ProjectApplyRecordResponse> responses = this.convertProjectApplyRecordResponse(projectApplyRecordList);
        return Result.<List<ProjectApplyRecordResponse>>builder().success().data(responses).build();
    }

    private List<ProjectApplyRecordResponse> convertProjectApplyRecordResponse(List<ProjectApplyRecord> projectApplyRecordList) {
        List<ProjectApplyRecordResponse> responses = Lists.newArrayList();
        projectApplyRecordList.forEach(projectApplyRecord -> {
            ProjectApplyRecordResponse recordResponse = new ProjectApplyRecordResponse();
            recordResponse.setApplyDate(projectApplyRecord.getAppleDate());
            recordResponse.setStatus(projectApplyRecord.getStatus());
            recordResponse.setId(projectApplyRecord.getId());
            if (projectApplyRecord.getCompanyDetail() != null){
                recordResponse.setCompanyName(projectApplyRecord.getCompanyDetail().getCompanyName());
                recordResponse.setContacts(projectApplyRecord.getCompanyDetail().getLinkMan());
                recordResponse.setContactNumber(projectApplyRecord.getCompanyDetail().getPhone());
            }
            recordResponse.setProjectName(projectApplyRecord.getProjectInfo().getProjectName());
            recordResponse.setProjectType(projectApplyRecord.getProjectInfo().getProjectType().getValue());
            recordResponse.setTypeId(projectApplyRecord.getProjectInfo().getProjectType().getId());
            recordResponse.setProjectAward(projectApplyRecord.getProjectInfo().getProjectPraise());
            responses.add(recordResponse);
        });
        return responses;
    }

    @Override
    public Result<String> deleteRecord(String recordId) {
        ProjectApplyRecord projectApplyRecord = this.checkProjectApplyRecord(recordId);
        projectApplyRecord.setDelete(Boolean.TRUE);
        projectApplyRecordRepository.save(projectApplyRecord);
        return Result.<String>builder().success().message("申请记录删除成功").build();
    }

    private ProjectApplyRecord checkProjectApplyRecord(String recordId) {
        Optional<ProjectApplyRecord> projectApplyRecordOptional = projectApplyRecordRepository.findByDeleteIsFalseAndId(recordId);
        if (!projectApplyRecordOptional.isPresent()){
            throw new NormalException("申请记录不存在");
        }
        return projectApplyRecordOptional.get();
    }

    @Override
    public Result<String> applyProject(String id) {
        ProjectInfo projectInfo = this.checkProjectInfo(id);
        ParkUser loginUser = tokenUtils.getLoginUser();
        Optional<ProjectApplyRecord> byId = projectApplyRecordRepository.findByDeleteIsFalseAndCompanyDetail_ParkUser_IdAndProjectInfo_Id(loginUser.getId(), id);
        if(byId.isPresent()){
            throw new NormalException("重复申请");
        }
        CompanyDetail companyDetail = this.checkCompanyDetailByUserId(loginUser.getId());
        ProjectApplyRecord projectApplyRecord = new ProjectApplyRecord();
        projectApplyRecord.setAppleDate(new Date());
        projectApplyRecord.setCompanyDetail(companyDetail);
        projectApplyRecord.setProjectInfo(projectInfo);
        projectApplyRecord.setDelete(Boolean.FALSE);
        projectApplyRecord.setAvailable(Boolean.TRUE);
        projectApplyRecord.setStatus(ProjectApplyStatus.APPLYING);
        projectApplyRecordRepository.save(projectApplyRecord);
        return Result.<String>builder().success().message("项目申请成功").build();
    }

    private CompanyDetail checkCompanyDetailByUserId(String userId) {
        Optional<CompanyDetail> companyDetailOptional = companyDetailRepository.findByParkUser_IdAndDeleteIsFalse(userId);
        if (!companyDetailOptional.isPresent()){
            throw new NormalException("当前账号未绑定企业，无法申请");
        }
        return companyDetailOptional.get();
    }

    @Override
    public Result<String> editProjectInfo(EditProjectInfoRequest request) {
        ProjectInfo projectInfo = this.checkProjectInfo(request.getId());
        projectInfo.setProjectName(request.getProjectName());
        projectInfo.setProjectPraise(request.getProjectPraise());
        projectInfo.setProjectRequire(request.getProjectRequire());
        if (StringUtils.isNotBlank(request.getTypeId())){
            ProjectType projectType = this.checkProjectType(request.getTypeId());
            projectInfo.setProjectType(projectType);
        }
        projectInfoRepository.save(projectInfo);
        return Result.<String>builder().success().message("编辑项目成功").build();
    }

    private ProjectInfo checkProjectInfo(String id) {
        Optional<ProjectInfo> projectInfoOptional = projectInfoRepository.findByDeleteIsFalseAndId(id);
        if (!projectInfoOptional.isPresent()){
            throw new NormalException("项目不存在");
        }
        return projectInfoOptional.get();
    }

    @Override
    public Result<Page<ProjectInfoResponse>> searchProjectInfo(SearchProjectInfoRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        Specification<ProjectInfo> specification = (Specification<ProjectInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateLists = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getProjectName())){
                predicateLists.add(criteriaBuilder.like(root.get("projectName").as(String.class),"%"+request.getProjectName()+"%"));
            }
            if (StringUtils.isNotBlank(request.getTypeId())){
                ProjectType projectInfoType = this.checkProjectType(request.getTypeId());
                predicateLists.add(criteriaBuilder.equal(root.get("projectType").as(ProjectType.class),projectInfoType));
            }
            predicateLists.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicateLists.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicateLists.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
            return criteriaBuilder.and(predicateLists.toArray(new Predicate[0]));
        };
        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findAll(specification,pageable);
        Page<ProjectInfoResponse> responses = this.convertProjectInfoResponsePage(projectInfoPage);
        return Result.<Page<ProjectInfoResponse>>builder().success().data(responses).build();
    }



    private Page<ProjectInfoResponse> convertProjectInfoResponsePage(Page<ProjectInfo> projectInfoPage) {
        List<ProjectInfoResponse> responses = Lists.newArrayList();
        projectInfoPage.getContent().forEach(projectInfo -> {
            ProjectInfoResponse response = new ProjectInfoResponse();
            BeanUtils.copyProperties(projectInfo,response);
            response.setTypeName(projectInfo.getProjectType().getValue());
            response.setTypeId(projectInfo.getProjectType().getId());
            responses.add(response);
        });
        return new PageImpl<>(responses,projectInfoPage.getPageable(),projectInfoPage.getTotalElements());
    }


    private ProjectType checkProjectType(String typeId) {
        Optional<ProjectType> projectTypeOptional = projectTypeRepository.findByDeleteIsFalseAndId(typeId);
        if (!projectTypeOptional.isPresent()){
            throw new NormalException("项目分类不存在");
        }
        return projectTypeOptional.get();
    }
}

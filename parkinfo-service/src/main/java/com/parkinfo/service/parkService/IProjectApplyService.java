package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.projectApply.AddProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.ChangeStatusRequest;
import com.parkinfo.request.parkService.projectApply.EditProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.SearchProjectInfoRequest;
import com.parkinfo.response.parkService.ProjectApplyRecordResponse;
import com.parkinfo.response.parkService.ProjectInfoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProjectApplyService {
    /**
     * 创建项目
     * @param request
     * @return
     */
    Result<String> addProjectInfo(AddProjectInfoRequest request);

    /**
     * 分页获取项目
     * @param request
     * @return
     */
    Result<Page<ProjectInfoResponse>> searchProjectInfo(SearchProjectInfoRequest request);

    /**
     * 编辑项目
     * @param request
     * @return
     */
    Result<String> editProjectInfo(EditProjectInfoRequest request);

    /**
     * 申请项目
     * @param id
     * @return
     */
    Result<String> applyProject(String id);

    /**
     * 查看企业
     * @param id
     * @return
     */
    Result<List<ProjectApplyRecordResponse>> searchCompany(String id);

    /**
     * 删除企业申请记录
     * @param recordId
     * @return
     */
    Result<String> deleteRecord(String recordId);

    /**
     * 查看我的申报
     * @return
     */
    Result<List<ProjectApplyRecordResponse>> searchMyApplyRecord();

    /**
     * 修改申报状态
     * @param request
     * @return
     */
    Result<String> changeStatus(ChangeStatusRequest request);

    Result<ProjectApplyRecordResponse> detailRecord(String recordId);
}

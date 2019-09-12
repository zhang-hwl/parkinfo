package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICompanyEnterService {

    /**
     * 导入入驻企业信息
     *
     * @param file
     * @return
     */
    Result enterImport(MultipartFile file);

    /**
     * 导出入驻企业信息
     *
     * @param response
     * @return
     */
    Result enterExport(HttpServletResponse response);

    /**
     * 查询所有入驻后企业
     * @param request
     * @return
     */
    Result<Page<EnterResponse>> findAll(QueryEnterRequest request);

    /**
     * 修改分页查询的企业信息
     * @param request
     * @return
     */
    Result modify(SetEnterRequest request);

    /**
     * 修改入驻企业详情
     * @param request
     * @return
     */
    Result setCompany(ModifyCompanyRequest request);

    /**
     * 添加入驻信息
     * @param request
     * @return
     */
    Result addEnter(AddEnterDetailRequest request);

    /**
     * 修改入驻信息
     * @param request
     * @return
     */
    Result set(SetEnterDetailRequest request);

    /**
     * 删除入驻信息
     * @param id
     * @return
     */
    Result deleteEnter(String id);

    /**
     * 查询入驻企业详情
     * @param id
     * @return
     */
    Result<EnterDetailResponse> query(String id);

    /**
     *查询入驻信息
     * @param id
     * @return
     */
    Result<List<EnteredInfo>> queryEnter(String id);

    /**
     * 查询所有附件
     * @param id
     * @return
     */
    Result<List<EnclosureTotal>> find(String id);

    /**
     * 删除入驻企业信息
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 上传文件
     * @param request
     * @return
     */
    Result<String> uploadFile(HttpServletRequest request);

    /**
     * 添加附件
     * @param request
     * @return
     */
    Result addFile(AddFileRequest request);
}
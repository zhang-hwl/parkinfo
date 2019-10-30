package com.parkinfo.service.personalCloud;

import com.parkinfo.common.Result;
import com.parkinfo.entity.personalCloud.CloudDisk;
import com.parkinfo.request.personalCloud.*;
import com.parkinfo.response.personalCloud.DownloadResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.response.personalCloud.UploadFileResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPersonalCloudService {

    /**
     * 查询云盘所有文件
     * @param request
     * @return
     */
    Result<Page<PersonalCloudResponse>> findAll(QueryPersonalCloudRequest request);

    Result<CloudDisk> uploadFile(HttpServletRequest request, UploadFileRequest fileRequest);

    Result<String> delete(String id);

    /**
     * 文件重命名
     * @param request
     * @return
     */
    Result<String> set(SetPersonalCloudRequest request);

    /**
     * 批量删除
     * @param request
     * @return
     */
    Result deleteAll(DeletePersonalCloudRequest request);

    /**
     * 确认上传文件
     * @param request
     * @return
     */
    Result<String> changeStatus(CloudDisk request);
}

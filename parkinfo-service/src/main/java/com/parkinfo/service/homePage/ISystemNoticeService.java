package com.parkinfo.service.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.notice.SystemNotice;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.notice.QueryNoticeRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ISystemNoticeService {

    Result<String> addNotice(SystemNotice systemNotice);

    Result<Page<SystemNotice>> findAll(QueryNoticeRequest request);

    Result<String> deleteById(String id);

    Result<List<SystemNotice>> findByLimit(Integer count);

}

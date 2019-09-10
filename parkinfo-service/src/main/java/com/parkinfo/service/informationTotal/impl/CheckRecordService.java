package com.parkinfo.service.informationTotal.impl;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import com.parkinfo.service.informationTotal.ICheckRecordService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class CheckRecordService implements ICheckRecordService {
    @Override
    public Result<String> addCheckRecord(CheckRecordRequest checkRecord) {
        return null;
    }

    @Override
    public Result<String> editCheckRecord(CheckRecordRequest checkRecord) {
        return null;
    }

    @Override
    public Result<List<CheckRecordRequest>> findByVersion(String version) {
        return null;
    }

    @Override
    public Result<String> checkRecordImport(MultipartFile file) {
        return null;
    }

    @Override
    public Result<String> checkRecordExport(HttpServletResponse response) {
        return null;
    }

    @Override
    public Result<List<CheckRecordRequest>> findAll() {
        return null;
    }

    @Override
    public Result<String> deleteCheckRecord(String id) {
        return null;
    }
}

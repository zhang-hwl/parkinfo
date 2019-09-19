package com.parkinfo.service.archiveInfo.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.repository.archiveInfo.ArchiveInfoTypeRepository;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveInfoTypeService implements IArchiveInfoTypeService {

    @Autowired
    private ArchiveInfoTypeRepository archiveInfoTypeRepository;

    @Override
    public Result<List<AllArchiveInfoTypeResponse>> findAll() {
        List<ArchiveInfoType> byAll = archiveInfoTypeRepository.findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();
        List<AllArchiveInfoTypeResponse> result = Lists.newArrayList();
        byAll.forEach(temp -> {
            AllArchiveInfoTypeResponse response = new AllArchiveInfoTypeResponse();
            BeanUtils.copyProperties(temp, response);
            List<ArchiveInfoTypeResponse> kindType = Lists.newArrayList();
            temp.getChildren().forEach(children -> {
                ArchiveInfoTypeResponse typeResponse = new ArchiveInfoTypeResponse();
                BeanUtils.copyProperties(children, typeResponse);
                kindType.add(typeResponse);
            });
            response.setKind(kindType);
            result.add(response);
        });
        return Result.<List<AllArchiveInfoTypeResponse>>builder().success().data(result).build();
    }
}

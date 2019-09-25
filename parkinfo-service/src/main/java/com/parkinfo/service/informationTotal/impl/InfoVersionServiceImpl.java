package com.parkinfo.service.informationTotal.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.InfoTotalVersion;
import com.parkinfo.repository.informationTotal.InfoVersionRepository;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoVersionServiceImpl implements IInfoVersionService {

    @Autowired
    private InfoVersionRepository infoVersionRepository;

    @Override
    public Result<String> add(InfoVersionResponse response) {
        InfoTotalVersion version = new InfoTotalVersion();
        version.setGeneral(response.getGeneral());
        version.setVersion(response.getVersion());
        version.setDelete(false);
        version.setAvailable(true);
        infoVersionRepository.save(version);
        return Result.<String>builder().success().data("添加成功").build();
    }

    @Override
    public Result<List<String>> findByGeneral(String general) {
        List<String> list = Lists.newArrayList();
        List<InfoTotalVersion> all = infoVersionRepository.findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(general);
        all.forEach(temp -> {
            list.add(temp.getVersion());
        });
        return Result.<List<String>>builder().success().data(list).build();
    }
}

package com.parkinfo.service.parkService.impl;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.ServiceFlowImgRepository;
import com.parkinfo.request.parkService.serviceFlow.AddServiceFlowImgRequest;
import com.parkinfo.request.parkService.serviceFlow.SearchServiceFlowImgRequest;
import com.parkinfo.service.parkService.IServiceFlowService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceFlowServiceImpl implements IServiceFlowService {

    @Autowired
    private ServiceFlowImgRepository serviceFlowImgRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Override
    public Result<ServiceFlowImg> searchServiceFlowImg(SearchServiceFlowImgRequest request) {
        ServiceFlowImg response = new ServiceFlowImg();
        Optional<ServiceFlowImg> serviceFlowImgOptional = serviceFlowImgRepository.findFirstByDeleteIsFalseAndImgTypeAndParkInfo(request.getImgType(),tokenUtils.getCurrentParkInfo());
        if (serviceFlowImgOptional.isPresent()){
            response = serviceFlowImgOptional.get();
        }
        return Result.<ServiceFlowImg>builder().success().data(response).build();
    }

    @Override
    public Result<String> editServiceFlowImg(AddServiceFlowImgRequest request) {
        ServiceFlowImg serviceFlowImg = new ServiceFlowImg();
        serviceFlowImg.setImgType(request.getImgType());
        if (StringUtils.isNotBlank(request.getId())){
            Optional<ServiceFlowImg> byId = serviceFlowImgRepository.findById(request.getId());
            if (byId.isPresent()){
                serviceFlowImg = byId.get();
            }
        }
        serviceFlowImg.setParkInfo(tokenUtils.getCurrentParkInfo());
        serviceFlowImg.setPath(request.getPath());
        serviceFlowImg.setDelete(Boolean.FALSE);
        serviceFlowImg.setAvailable(Boolean.TRUE);
        serviceFlowImgRepository.save(serviceFlowImg);
        return Result.<String>builder().success().message("编辑或上传成功").build();
    }

    @Override
    public Result<ServiceFlowImg> detailServiceFlowImg(String id) {
        Optional<ServiceFlowImg> byIdAndDeleteIsFalseAndAvailableIsTrue = serviceFlowImgRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byIdAndDeleteIsFalseAndAvailableIsTrue.isPresent()){
            throw new NormalException("流程图不存在");
        }
        ServiceFlowImg serviceFlowImg = byIdAndDeleteIsFalseAndAvailableIsTrue.get();
        return Result.<ServiceFlowImg>builder().success().data(serviceFlowImg).build();
    }
}

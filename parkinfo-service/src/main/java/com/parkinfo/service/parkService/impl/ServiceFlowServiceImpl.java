package com.parkinfo.service.parkService.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.repository.parkService.ServiceFlowImgRepository;
import com.parkinfo.request.parkService.serviceFlow.SearchServiceFlowImgRequest;
import com.parkinfo.service.parkService.IServiceFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceFlowServiceImpl implements IServiceFlowService {

    @Autowired
    private ServiceFlowImgRepository serviceFlowImgRepository;
    @Override
    public Result<ServiceFlowImg> searchServiceFlowImg(SearchServiceFlowImgRequest request) {
        ServiceFlowImg response = new ServiceFlowImg();
        Optional<ServiceFlowImg> serviceFlowImgOptional = serviceFlowImgRepository.findByDeleteIsFalseAndImgType(request.getImgType());
        if (serviceFlowImgOptional.isPresent()){
            response = serviceFlowImgOptional.get();
        }
        return Result.<ServiceFlowImg>builder().success().data(response).build();
    }
}

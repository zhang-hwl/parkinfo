package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.enums.ServiceFlowImgType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceFlowImgRepository extends JpaRepository<ServiceFlowImg,String> {

    Optional<ServiceFlowImg> findByDeleteIsFalseAndImgType(ServiceFlowImgType type);
}

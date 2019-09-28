package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CompeteGradenInfoRepository extends JpaRepository<CompeteGradenInfo, String>, JpaSpecificationExecutor<CompeteGradenInfo> {

    Optional<CompeteGradenInfo> findByIdAndDeleteIsFalse(String id);

    List<CompeteGradenInfo> findAllByDeleteIsFalse();

    List<CompeteGradenInfo> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

}

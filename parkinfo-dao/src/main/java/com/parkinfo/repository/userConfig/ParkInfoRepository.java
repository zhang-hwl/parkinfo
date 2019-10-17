package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ParkInfoRepository extends JpaRepository<ParkInfo,String>, JpaSpecificationExecutor<ParkInfo> {

    Optional<ParkInfo> findByIdAndDeleteIsFalse(String id);

    Optional<ParkInfo> findFirstByDeleteIsFalseAndAvailableIsTrueAndId(String parkId);

    List<ParkInfo> findAllByDeleteIsFalseAndAvailableIsTrue();

    Optional<ParkInfo> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    Optional<ParkInfo> findByIdAndDeleteIsFalseAndAvailableIsTrueAndManagerIsNull(String id);

    Optional<ParkInfo> findByNameAndDeleteIsFalseAndAvailableIsTrue(String name);
}

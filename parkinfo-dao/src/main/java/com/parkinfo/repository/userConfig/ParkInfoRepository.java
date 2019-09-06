package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkInfoRepository extends JpaRepository<ParkInfo,String> {
    Optional<ParkInfo> findFirstByDeleteIsFalseAndAvailableIsTrueAndId(String parkId);
}

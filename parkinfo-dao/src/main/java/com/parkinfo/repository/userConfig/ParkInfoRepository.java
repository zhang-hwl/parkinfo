package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkInfoRepository extends JpaRepository<ParkInfo,String> {
}

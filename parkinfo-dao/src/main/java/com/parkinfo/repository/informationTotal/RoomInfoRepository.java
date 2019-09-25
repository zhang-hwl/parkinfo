package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, String>, JpaSpecificationExecutor<RoomInfo> {

    Optional<RoomInfo> findByIdAndDeleteIsFalse(String id);

    List<RoomInfo> findByVersionAndDeleteIsFalse(String version);

    List<RoomInfo> findAllByDeleteIsFalse();

    List<RoomInfo> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

}

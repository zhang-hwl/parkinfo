package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, String> {

    Optional<RoomInfo> findByIdAndDeleteIsFalse(String id);

    List<RoomInfo> findByVersionAndDeleteIsFalse(String version);

    List<RoomInfo> findAllByDeleteIsFalse();

}

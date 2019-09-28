package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BigEventRepository extends JpaRepository<BigEvent, String>, JpaSpecificationExecutor<BigEvent> {

    Optional<BigEvent> findByIdAndDeleteIsFalse(String id);

    List<BigEvent> findByVersionAndDeleteIsFalse(String version);

    List<BigEvent> findAllByDeleteIsFalse();

    List<BigEvent> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

}

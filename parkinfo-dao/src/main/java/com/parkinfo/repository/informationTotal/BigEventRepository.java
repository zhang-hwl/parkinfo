package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BigEventRepository extends JpaRepository<BigEvent, String>, JpaSpecificationExecutor<BigEvent> {

    Optional<BigEvent> findByIdAndDeleteIsFalse(String id);

    List<BigEvent> findByVersionAndDeleteIsFalse(String version);

    List<BigEvent> findAllByDeleteIsFalse();

    List<BigEvent> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    @Query(nativeQuery = true, value = "select * from c_big_event where `delete`='0' and `available`='1' order by year,month")
    List<BigEvent> findAllOrder();

    @Query(nativeQuery = true, value = "select * from c_big_event where park_info_id=?1 and `delete`='0' and `available`='1' order by year,month")
    List<BigEvent> findAllOrderAndPark(String parkId);

}

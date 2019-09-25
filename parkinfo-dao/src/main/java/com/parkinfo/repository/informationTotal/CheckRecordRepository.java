package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.CheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CheckRecordRepository extends JpaRepository<CheckRecord, String>, JpaSpecificationExecutor<CheckRecord> {

    Optional<CheckRecord> findByIdAndDeleteIsFalse(String id);

    List<CheckRecord> findByVersionAndDeleteIsFalse(String version);

    List<CheckRecord> findAllByDeleteIsFalse();

    List<CheckRecord> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

}

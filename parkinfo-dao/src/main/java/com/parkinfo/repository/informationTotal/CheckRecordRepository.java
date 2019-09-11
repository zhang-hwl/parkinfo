package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.CheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckRecordRepository extends JpaRepository<CheckRecord, String> {

    Optional<CheckRecord> findByIdAndDeleteIsFalse(String id);

    List<CheckRecord> findByVersionAndDeleteIsFalse(String version);

    List<CheckRecord> findAllByDeleteIsFalse();

}

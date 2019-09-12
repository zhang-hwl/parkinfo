package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.InfoEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoEquipmentRepository extends JpaRepository<InfoEquipment, String> {

    Optional<InfoEquipment> findByIdAndDeleteIsFalse(String id);

    List<InfoEquipment> findByVersionAndDeleteIsFalse(String version);

    List<InfoEquipment> findAllByDeleteIsFalse();

}

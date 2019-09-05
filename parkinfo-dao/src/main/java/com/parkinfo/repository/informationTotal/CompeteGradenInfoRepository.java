package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompeteGradenInfoRepository extends JpaRepository<CompeteGradenInfo, String> {

    Optional<CompeteGradenInfo> findByIdAndDeleteIsFalse(String id);

    List<CompeteGradenInfo> findByVersionAndDeleteIsFalse(String version);

    List<CompeteGradenInfo> findAllByDeleteIsFalse();

}

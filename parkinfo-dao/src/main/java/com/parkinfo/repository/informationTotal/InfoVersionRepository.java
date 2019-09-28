package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.InfoTotalVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InfoVersionRepository extends JpaRepository<InfoTotalVersion, String>, JpaSpecificationExecutor<InfoTotalVersion> {

    List<InfoTotalVersion> findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(String general);

    Optional<InfoTotalVersion> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);



}

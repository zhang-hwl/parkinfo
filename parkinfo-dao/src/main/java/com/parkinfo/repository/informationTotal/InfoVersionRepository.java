package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.InfoTotalVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoVersionRepository extends JpaRepository<InfoTotalVersion, String> {

    List<InfoTotalVersion> findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(String general);



}

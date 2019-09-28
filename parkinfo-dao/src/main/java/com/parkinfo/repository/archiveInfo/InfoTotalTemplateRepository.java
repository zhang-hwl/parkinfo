package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.informationTotal.InfoTotalTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoTotalTemplateRepository extends JpaRepository<InfoTotalTemplate, String> {

    Optional<InfoTotalTemplate> findByTypeAndDeleteIsFalseAndAvailableIsTrue(String type);

    Optional<InfoTotalTemplate> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<InfoTotalTemplate> findAllByDeleteIsFalseAndAvailableIsTrue();

}

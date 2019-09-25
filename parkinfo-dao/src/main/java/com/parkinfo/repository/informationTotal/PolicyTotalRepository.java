package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.PolicyTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PolicyTotalRepository extends JpaRepository<PolicyTotal, String>, JpaSpecificationExecutor<PolicyTotal> {

    Optional<PolicyTotal> findByIdAndDeleteIsFalse(String id);

    List<PolicyTotal> findByVersionAndDeleteIsFalse(String version);

    List<PolicyTotal> findAllByDeleteIsFalse();

    List<PolicyTotal> findByParkInfo_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<PolicyTotal> findByParkInfo_IdAndVersionAndDeleteIsFalseAndAvailableIsTrue(String id, String version);

}

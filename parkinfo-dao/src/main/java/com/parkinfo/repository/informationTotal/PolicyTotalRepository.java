package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.PolicyTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyTotalRepository extends JpaRepository<PolicyTotal, String> {

    Optional<PolicyTotal> findByIdAndDeleteIsFalse(String id);

    List<PolicyTotal> findByVersionAndDeleteIsFalse(String version);

    List<PolicyTotal> findAllByDeleteIsFalse();

}

package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkRoleRepository extends JpaRepository<ParkRole,String> {

    List<ParkRole> findAllByDeleteIsFalseAndAvailableIsTrue();

}

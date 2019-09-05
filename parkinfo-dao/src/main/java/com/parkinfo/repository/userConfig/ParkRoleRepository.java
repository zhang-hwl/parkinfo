package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRoleRepository extends JpaRepository<ParkRole,String> {
}

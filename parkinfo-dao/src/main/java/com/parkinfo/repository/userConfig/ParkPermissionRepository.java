package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkPermissionRepository extends JpaRepository<ParkPermission,String> {
}

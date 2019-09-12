package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkPermissionRepository extends JpaRepository<ParkPermission,String> {

    List<ParkPermission> findAllByParentIsNullAndAvailableIsTrueAndDeleteIsFalse();
}

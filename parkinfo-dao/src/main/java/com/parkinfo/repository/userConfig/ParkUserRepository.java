package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkUserRepository extends JpaRepository<ParkUser,String> {
}

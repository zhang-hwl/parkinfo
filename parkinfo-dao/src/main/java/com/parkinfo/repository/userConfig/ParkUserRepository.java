package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkUserRepository extends JpaRepository<ParkUser,String> {

    Optional<ParkUser> findByIdAndDeleteIsFalse(String id);

}

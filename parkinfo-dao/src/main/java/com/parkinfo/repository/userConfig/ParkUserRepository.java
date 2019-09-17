package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkUserRepository extends JpaRepository<ParkUser,String> {

    Page<ParkUser> findAll(Specification<ParkUser> specification, Pageable pageable);

    Optional<ParkUser> findByIdAndDeleteIsFalse(String id);

    Optional<ParkUser> findByAccountAndAvailableIsTrueAndDeleteIsFalse(String account);

    Optional<ParkUser> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<ParkUser> findAllByParksEqualsAndDeleteIsFalseAndAvailableIsTrue(ParkInfo parkInfo);
}

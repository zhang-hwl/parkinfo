package com.parkinfo.repository.userConfig;

import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkUserRepository extends JpaRepository<ParkUser,String> {

    Page<ParkUser> findAll(Specification<ParkUser> specification, Pageable pageable);

    Optional<ParkUser> findByIdAndDeleteIsFalse(String id);

    Optional<ParkUser> findByAccountAndAvailableIsTrueAndDeleteIsFalse(String account);

    Optional<ParkUser> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<ParkUser> findAllByParksEqualsAndDeleteIsFalseAndAvailableIsTrue(ParkInfo parkInfo);

    Optional<ParkUser> findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    @Query(nativeQuery = true, value = "select up.user_id from c_user_park up left join c_park_user pu on up.user_id = pu.id where up.park_id=?1 and pu.`delete`=0")
    List<String> fingAllByParkInfoId(String id);

    Optional<ParkUser> findByIdAndAvailableIsTrueAndDeleteIsFalse(String id);

    List<ParkUser> findByDeleteIsFalse();
}

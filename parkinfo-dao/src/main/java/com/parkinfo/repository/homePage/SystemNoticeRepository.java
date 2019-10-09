package com.parkinfo.repository.homePage;

import com.parkinfo.entity.notice.SystemNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SystemNoticeRepository extends JpaRepository<SystemNoticeEntity, String>, JpaSpecificationExecutor<SystemNoticeEntity> {

    Optional<SystemNoticeEntity> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    @Query(nativeQuery = true, value = "select * from c_system_notice where `delete`=0 and available=1 order by create_time DESC limit ?1")
    List<SystemNoticeEntity> findByLimit(Integer count);

}

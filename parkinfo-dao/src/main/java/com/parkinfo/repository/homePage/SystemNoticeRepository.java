package com.parkinfo.repository.homePage;

import com.parkinfo.entity.notice.SystemNotice;
import com.parkinfo.entity.notice.SystemNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SystemNoticeRepository extends JpaRepository<SystemNoticeEntity, String>, JpaSpecificationExecutor<SystemNoticeEntity> {

    Optional<SystemNoticeEntity> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

}

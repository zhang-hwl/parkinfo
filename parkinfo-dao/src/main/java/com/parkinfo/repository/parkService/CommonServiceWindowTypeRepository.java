package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonServiceWindowTypeRepository extends JpaRepository<CommonServiceWindowType,String> {
    Optional<CommonServiceWindowType> findByDeleteIsFalseAndId(String id);
}
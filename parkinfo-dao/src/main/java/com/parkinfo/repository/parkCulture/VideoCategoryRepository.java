package com.parkinfo.repository.parkCulture;


import com.parkinfo.entity.parkCulture.VideoCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoCategoryRepository extends JpaRepository<VideoCategory,String> {

    Page<VideoCategory> findAll(Specification<VideoCategory> videoCategorySpecification, Pageable pageable);

    Optional<VideoCategory> findByIdAndDeleteIsFalseAndAvailableIsTrue(String parentId);
}

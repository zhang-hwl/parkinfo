package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepository extends JpaRepository<VideoComment,String> {
    Page<VideoComment> findAll(Specification<VideoComment> videoCommentSpecification, Pageable pageable);
}

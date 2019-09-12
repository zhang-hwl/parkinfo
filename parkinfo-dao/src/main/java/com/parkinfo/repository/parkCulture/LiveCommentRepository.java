package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.LiveComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveCommentRepository extends JpaRepository<LiveComment,String> {
    Page<LiveComment> findAll(Specification<LiveComment> liveCommentSpecification, Pageable pageable);
}

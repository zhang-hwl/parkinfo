package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.BookComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment,String> {
    Page<BookComment> findAll(Specification<BookComment> bookCommentSpecification, Pageable pageable);
}

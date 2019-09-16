package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCategoryRepository extends JpaRepository<BookCategory,String> {
    Page<BookCategory> findAll(Specification<BookCategory> bookCategorySpecification, Pageable pageable);

    Optional<BookCategory> findByIdAndDeleteIsFalseAndAvailableIsTrue(String parentId);
}

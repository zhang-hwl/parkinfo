package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,String> {
    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);

    Optional<Book> findByIdAndDeleteIsFalseAndAvailableIsTrue(String bookId);
}

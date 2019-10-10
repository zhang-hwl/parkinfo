package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.ReadProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadProcessRepository extends JpaRepository<ReadProcess,String> {
    Optional<ReadProcess> findByBook_IdAndDeleteIsTrueAndAvailableIsFalse(String bookId);

    Page<ReadProcess> findAll(Specification<ReadProcess> readProcessSpecification, Pageable pageable);

    Optional<ReadProcess> findByBook_IdAndReader_IdAndDeleteIsFalseAndAvailableIsTrue(String bookId, String id);
}

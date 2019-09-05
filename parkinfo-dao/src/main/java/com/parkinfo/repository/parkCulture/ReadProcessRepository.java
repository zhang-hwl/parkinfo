package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.ReadProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadProcessRepository extends JpaRepository<ReadProcess,String> {
    Optional<ReadProcess> findByBook_IdAndDeleteIsTrueAndAvailableIsFalse(String bookId);
}

package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.DeclarePaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeclarePaperRepository extends JpaRepository<DeclarePaper, String> {

    Page<DeclarePaper> findAll(Specification<DeclarePaper> staffSalarySpecification , Pageable pageable);

    Optional<DeclarePaper> findById(String id);

    List<DeclarePaper> findAllByDeclareType(String declareType);

}

package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.Live;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LiveRepository extends JpaRepository<Live,String> {
    Page<Live> findAll(Specification<Live> liveSpecification, Pageable pageable);

    Optional<Live> findFirstByAppNameAndStreamName(String appname, String id);
}

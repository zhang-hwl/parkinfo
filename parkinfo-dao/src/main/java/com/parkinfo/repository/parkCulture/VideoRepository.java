package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.Book;
import com.parkinfo.entity.parkCulture.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,String> {

    Page<Video> findAll(Specification<Video> videoSpecification, Pageable pageable);
}

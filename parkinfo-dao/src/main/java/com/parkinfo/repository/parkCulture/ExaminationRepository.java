package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examination,String> {
}

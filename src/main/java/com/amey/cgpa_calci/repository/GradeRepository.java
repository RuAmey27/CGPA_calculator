package com.amey.cgpa_calci.repository;

import com.amey.cgpa_calci.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findBySubjectId(Long subjectId);
}

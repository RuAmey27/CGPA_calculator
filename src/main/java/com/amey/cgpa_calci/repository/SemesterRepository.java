package com.amey.cgpa_calci.repository;

import com.amey.cgpa_calci.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    List<Semester> findByDepartmentId(Long departmentId);
}

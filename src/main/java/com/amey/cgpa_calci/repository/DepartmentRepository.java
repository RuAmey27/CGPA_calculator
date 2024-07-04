package com.amey.cgpa_calci.repository;

import com.amey.cgpa_calci.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

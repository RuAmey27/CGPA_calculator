package com.amey.cgpa_calci.repository;

import com.amey.cgpa_calci.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//public interface SubjectRepository extends JpaRepository<Subject, Long> {
//    List<Subject> findBySemesterId(Long semesterId);
//}
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByDepartmentIdAndSemesterId(Long departmentId, Long semesterId);
}

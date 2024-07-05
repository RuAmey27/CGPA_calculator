package com.amey.cgpa_calci.Controller;

import com.amey.cgpa_calci.entity.*;
import com.amey.cgpa_calci.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@Controller
@RequestMapping("/cgpa")
public class CGPAController {

    private static final Logger logger = LoggerFactory.getLogger(CGPAController.class);
    private final DepartmentRepository departmentRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public CGPAController(DepartmentRepository departmentRepository, SemesterRepository semesterRepository,
                          SubjectRepository subjectRepository, GradeRepository gradeRepository) {
        this.departmentRepository = departmentRepository;
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
        this.gradeRepository = gradeRepository;
    }

    @GetMapping("/calculator")
    public String showCGPACalculator(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        return "cgpa_calculator";
    }

    @GetMapping("/subjects")
    @ResponseBody
    public List<Subject> fetchSubjects(@RequestParam Long departmentId, @RequestParam Long semesterId) {
        logger.info("Fetching subjects for Department ID: {} and Semester ID: {}", departmentId, semesterId);
        return subjectRepository.findByDepartmentIdAndSemesterId(departmentId, semesterId);
    }

    @PostMapping("/calculate")
    public String calculateCGPA(@RequestParam Long departmentId, @RequestParam Long semesterId,
                                @RequestParam Map<String, String> allParams, Model model) {
        List<Subject> subjects = subjectRepository.findByDepartmentIdAndSemesterId(departmentId, semesterId);
        double totalCredits = 0;
        double totalGradePoints = 0;

        for (Subject subject : subjects) {
            String gradeValue = allParams.get("grade_" + subject.getId());
            if (gradeValue != null && !gradeValue.isEmpty()) {
                double gradePoint = getGradePoint(gradeValue);
                totalGradePoints += gradePoint * subject.getCredits();
                totalCredits += subject.getCredits();
            }
        }

        double cgpa = (totalCredits > 0) ? (totalGradePoints / totalCredits) : 0.0;
        model.addAttribute("cgpa", cgpa);
        return "cgpa_result";
    }

    private double getGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A+": return 10;
            case "A": return 9;
            case "B+": return 8;
            case "B": return 7;
            case "C": return 6;
            case "D": return 5;
            case "E": return 4;
            default: return 0;
        }
    }
}

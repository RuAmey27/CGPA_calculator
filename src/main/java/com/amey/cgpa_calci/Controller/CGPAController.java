package com.amey.cgpa_calci.Controller;

import com.amey.cgpa_calci.entity.*;
import com.amey.cgpa_calci.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cgpa")
public class CGPAController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/calculator")
    public String showCGPACalculator(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("grades", gradeRepository.findAll());
        return "cgpa_calculator";
    }

    // Handle form submission to calculate CGPA
    @PostMapping("/calculate")
    public String calculateCGPA(@RequestParam Long semesterId, @RequestParam Long subjectId, Model model) {
        // Retrieve subjects and grades for selected semester and subject
        List<Subject> subjects = subjectRepository.findBySemesterId(semesterId);
        List<Grade> grades = gradeRepository.findBySubjectId(subjectId);

        // Perform CGPA calculation logic
        double totalCredits = 0;
        double totalGradePoints = 0;

        for (Subject subject : subjects) {
            Grade grade = grades.stream().filter(g -> g.getSubject().getId().equals(subject.getId())).findFirst().orElse(null);
            if (grade != null) {
                double gradePoint = getGradePoint(grade.getGrade());
                totalGradePoints += gradePoint * subject.getCredits();
                totalCredits += subject.getCredits();
            }
        }

        double cgpa = totalGradePoints / totalCredits;
        model.addAttribute("cgpa", cgpa);

        return "cgpa_result";
    }

    private double getGradePoint(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }
    }
}

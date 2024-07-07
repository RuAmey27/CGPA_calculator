package com.amey.cgpa_calci.Controller;

import com.amey.cgpa_calci.entity.Department;
import com.amey.cgpa_calci.entity.Semester;
import com.amey.cgpa_calci.entity.Subject;
import com.amey.cgpa_calci.repository.DepartmentRepository;
import com.amey.cgpa_calci.repository.SemesterRepository;
import com.amey.cgpa_calci.repository.SubjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DepartmentRepository departmentRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    public AdminController(DepartmentRepository departmentRepository, SemesterRepository semesterRepository, SubjectRepository subjectRepository) {
        this.departmentRepository = departmentRepository;
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/admin_dashboard";
    }

    @GetMapping("/add-subject")
    public String showAddSubjectForm(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        return "admin/add_subject";
    }

    @PostMapping("/add-subject")
    public String addSubject(@RequestParam Long semesterId, @RequestParam Long departmentId, @RequestParam String subjectName, @RequestParam int credits) {
        Semester semester = semesterRepository.findById(semesterId).orElse(null);
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (semester != null && department != null) {
            Subject subject = new Subject();
            subject.setName(subjectName);
            subject.setCredits(credits);
            subject.setSemester(semester);
            subject.setDepartment(department);
            subjectRepository.save(subject);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/add-semester")
    public String showAddSemesterForm(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/add_semester";
    }

    @PostMapping("/add-semester")
    public String addSemester(@RequestParam Long departmentId, @RequestParam String semesterName) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            Semester semester = new Semester();
            semester.setName(semesterName);
            semester.setDepartment(department);
            semesterRepository.save(semester);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/add-department")
    public String showAddDepartmentForm(Model model) {
        return "admin/add_department";
    }

    @PostMapping("/add-department")
    public String addDepartment(@RequestParam String departmentName) {
        Department department = new Department();
        department.setName(departmentName);
        departmentRepository.save(department);
        return "redirect:/admin/dashboard";
    }
}

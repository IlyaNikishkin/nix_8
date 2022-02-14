package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.request.StudentRequestDto;
import ua.com.alevel.entity.Student;
import ua.com.alevel.facade.impl.GroupFacadeImpl;
import ua.com.alevel.facade.impl.StudentFacadeImpl;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentFacadeImpl studentFacadeImpl;
    private final GroupFacadeImpl groupFacadeImpl;
    String message;

    public StudentController(StudentFacadeImpl studentFacadeImpl, GroupFacadeImpl groupFacadeImpl) {
        this.studentFacadeImpl = studentFacadeImpl;
        this.groupFacadeImpl = groupFacadeImpl;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("students", studentFacadeImpl.findAll());
        return "student/student_all";
    }

    @GetMapping("/new")
    public String redirectToNew(Model model) {
        model.addAttribute("student", new StudentRequestDto());
        model.addAttribute("groups", groupFacadeImpl.findAll());
        model.addAttribute("message", message);
        return "student/student_new";
    }

    @PostMapping("/new")
    public String newStudent(@ModelAttribute StudentRequestDto dto) {
        if (dto.getName().isBlank() || dto.getAge() < 0 || notUniqueNewStudent(dto)) {
            message = "Name is blank or Age < 0 or Student already exists";
            return "redirect:/students/new";
        }
        message = "";
        studentFacadeImpl.create(dto);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String updateStudentPage(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentFacadeImpl.findById(id));
        model.addAttribute("groups", groupFacadeImpl.findAll());
        model.addAttribute("selectedGroup", studentFacadeImpl.findById(id).getGroup().getId());
        model.addAttribute("message", message);
        return "student/student_update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@ModelAttribute StudentRequestDto dto, @PathVariable Long id) {
        if (dto.getName().isBlank() || dto.getAge() < 0 || notUniqueUpdateStudent(dto)) {
            message = "Name is blank or Age < 0 or Student already exists";
            return "redirect:/students/update/{id}";
        }
        message = "";
        studentFacadeImpl.update(dto, id);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentFacadeImpl.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/to-new")
    public String redirectToNew() {
        message = "";
        return "redirect:/students/new";
    }

    @GetMapping("/to-update/{id}")
    public String redirectToUpdate(@PathVariable Long id) {
        message = "";
        return "redirect:/students/update/{id}";
    }

    private boolean notUniqueNewStudent(StudentRequestDto dto) {
        List<Student> students = studentFacadeImpl.findAll();
        int i;
        for (i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(dto.getName()) && students.get(i).getAge() == dto.getAge()) {
                i = -1;
                break;
            }
        }
        return i == -1;
    }

    private boolean notUniqueUpdateStudent(StudentRequestDto dto) {
        List<Student> students = studentFacadeImpl.findAll();
        int i;
        for (i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(dto.getName())
                    && students.get(i).getAge() == dto.getAge()
                    && students.get(i).getGroup().getId().equals(dto.getGroupId())) {
                i = -1;
                break;
            }
        }
        return i == -1;
    }
}

package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.request.GroupRequestDto;
import ua.com.alevel.dto.request.StudentRequestDto;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;
import ua.com.alevel.facade.impl.GroupFacadeImpl;
import ua.com.alevel.facade.impl.StudentFacadeImpl;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupFacadeImpl groupFacadeImpl;
    private final StudentFacadeImpl studentFacadeImpl;
    String message;

    public GroupController(GroupFacadeImpl groupFacadeImpl, StudentFacadeImpl studentFacadeImpl) {
        this.groupFacadeImpl = groupFacadeImpl;
        this.studentFacadeImpl = studentFacadeImpl;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("groups", groupFacadeImpl.findAll());
        return "group/group_all";
    }

    @GetMapping("/new")
    public String newGroup(Model model) {
        model.addAttribute("group", new GroupRequestDto());
        model.addAttribute("message", message);
        return "group/group_new";
    }

    @GetMapping("/to-new")
    public String redirectToNew() {
        message = "";
        return "redirect:/groups/new";
    }

    @GetMapping("/{id}/students/to-new")
    public String redirectToNewStudentInGroup(@PathVariable Long id) {
        message = "";
        return "redirect:/groups/{id}/students/new";
    }

    @GetMapping("/to-update/{id}")
    public String redirectToUpdate(@PathVariable Long id) {
        message = "";
        return "redirect:/groups/update/{id}";
    }

    @GetMapping("/{groupId}/students/to-update/{studentId}")
    public String redirectToUpdateStudentInGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        message = "";
        return "redirect:/groups/{groupId}/students/update/{studentId}";
    }

    @GetMapping("/{groupId}/students/update/{studentId}")
    public String updateStudentInGroup(@PathVariable Long groupId, @PathVariable Long studentId, Model model) {
        model.addAttribute("student", studentFacadeImpl.findById(studentId));
        model.addAttribute("groups", groupFacadeImpl.findAll());
        model.addAttribute("selectedGroup", groupId);
        model.addAttribute("message", message);
        return "student/student_update_in_group";
    }

    @PostMapping("/{groupId}/students/update/{studentId}")
    public String updateStudentInGroup(@PathVariable Long groupId, @PathVariable Long studentId, @ModelAttribute StudentRequestDto dto) {
        if (dto.getName().isBlank() || dto.getAge() < 0 || notUniqueUpdateStudent(dto)) {
            message = "Name is blank or Age < 0 or Student already exists";
            return "redirect:/groups/{groupId}/students/update/{studentId}";
        }
        message = "";
        studentFacadeImpl.update(dto, studentId);
        return "redirect:/groups/{groupId}/students";
    }

    @PostMapping("/new")
    public String newGroup(@ModelAttribute GroupRequestDto dto) {
        if (dto.getName().isBlank() || notUniqueGroup(dto)) {
            message = "Name is blank or Group already exists";
            return "redirect:/groups/new";
        }
        message = "";
        groupFacadeImpl.create(dto);
        return "redirect:/groups";
    }

    @GetMapping("/update/{id}")
    public String updateGroupPage(@PathVariable Long id, Model model) {
        model.addAttribute("group", groupFacadeImpl.findById(id));
        model.addAttribute("message", message);
        return "group/group_update";
    }

    @PostMapping("/update/{id}")
    public String updateGroup(@ModelAttribute GroupRequestDto dto, @PathVariable Long id) {
        if (dto.getName().isBlank() || notUniqueGroup(dto)) {
            message = "Name is blank or Group already exists";
            return "redirect:/groups/update/{id}";
        }
        message = "";
        groupFacadeImpl.update(dto, id);
        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        groupFacadeImpl.delete(id);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/students")
    public String findAllByGroup(Model model, @PathVariable Long id) {
        model.addAttribute("studentsInGroup", groupFacadeImpl.findAllStudentsByGroup(id));
        model.addAttribute("groupName", groupFacadeImpl.findById(id).getName());
        return "student/students_in_group";
    }

    @GetMapping("/{id}/students/new")
    public String redirectToNewStudentInGroup(@PathVariable Long id, Model model) {
        model.addAttribute("student", new StudentRequestDto());
        model.addAttribute("group", groupFacadeImpl.findById(id));
        model.addAttribute("groupName", groupFacadeImpl.findById(id).getName());
        model.addAttribute("message", message);
        return "student/student_new_in_group";
    }

    @PostMapping("/{id}/students/new")
    public String newStudentInGroup(@PathVariable Long id, @ModelAttribute StudentRequestDto dto) {
        if (dto.getName().isBlank() || dto.getAge() < 0 || notUniqueNewStudent(dto)) {
            message = "Name is blank or Age < 0 or Student already exists";
            return "redirect:/groups/{id}/students/new";
        }
        message = "";
        dto.setGroupId(id);
        studentFacadeImpl.create(dto);
        return "redirect:/groups/{id}/students";
    }

    @GetMapping("/{groupId}/students/delete/{studentId}")
    public String deleteStudentInGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        studentFacadeImpl.delete(studentId);
        return "redirect:/groups/{groupId}/students";
    }

    private boolean notUniqueGroup(GroupRequestDto dto) {
        List<Group> groups = groupFacadeImpl.findAll();
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getName().equals(dto.getName())) {
                i = -1;
                break;
            }
        }
        return i == -1;
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
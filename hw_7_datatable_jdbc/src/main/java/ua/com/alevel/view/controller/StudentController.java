package ua.com.alevel.view.controller;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.GroupFacade;
import ua.com.alevel.facade.StudentFacade;
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.GroupResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/students")
public class StudentController extends BaseController {

    private long update_id;
    private final GroupFacade groupFacade;
    private final StudentFacade studentFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("age", "age", "age"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public StudentController(GroupFacade groupFacade, StudentFacade studentFacade) {
        this.groupFacade = groupFacade;
        this.studentFacade = studentFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        initDataTable(studentFacade.findAll(request), columnNames, model);
        model.addAttribute("createUrl", "/students/all");
        model.addAttribute("createNew", "null");
        model.addAttribute("cardHeader", "All Students");
        return "pages/students/students_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "students");
    }

    @GetMapping("/groups/{id}")
    public String findAllByGroup(Model model, @PathVariable Long id, WebRequest request) {
        model.addAttribute("group", groupFacade.findById(id));
        initDataTable(studentFacade.findAllStudentsByGroup(request, id), columnNames, model);
        model.addAttribute("createUrl", "/students/all");
        model.addAttribute("createNew", "/students/new/" + id);
        model.addAttribute("cardHeader", "All Students");
        return "pages/students/students_in_group";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable @Valid @Min(value = 1, message = "invalid id") @NotNull() Long id, Model model) {
        StudentResponseDto dto = studentFacade.findById(id);
        model.addAttribute("student", dto);
        return "pages/students/student_details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentFacade.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String updateStudentPage(@PathVariable @Valid @Min(value = 1, message = "invalid id") @NotNull() Long id, @ModelAttribute("student") StudentRequestDto dto, Model model, WebRequest request) {
        update_id = id;
        PageData<GroupResponseDto> groups = groupFacade.findAll(request);

        model.addAttribute("groupsList", groupFacade.findAll(request));
        return "pages/students/student_update";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") StudentRequestDto dto) {
        studentFacade.update(dto, update_id);
        return "redirect:/students";
    }

    @GetMapping("/new/{id}")
    public String redirectToNewStudentPage(Model model, @PathVariable Long id) {
        StudentRequestDto student = new StudentRequestDto();
        student.setGroupId(id);
        model.addAttribute("student", student);
        return "pages/students/student_new";
    }

    @PostMapping("/new")
    public String createNewStudent(@ModelAttribute("group") StudentRequestDto dto) {
        studentFacade.create(dto);
        String url = "redirect:/students/groups/" + dto.getGroupId();
        return url;
    }
}

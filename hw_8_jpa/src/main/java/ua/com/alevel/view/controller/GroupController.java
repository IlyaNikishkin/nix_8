package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.GroupFacade;
import ua.com.alevel.view.dto.request.GroupRequestDto;
import ua.com.alevel.view.dto.response.GroupResponseDto;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/groups")
public class GroupController extends BaseController {

    private long new_id;
    private final GroupFacade groupFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public GroupController(GroupFacade groupFacade) {
        this.groupFacade = groupFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<GroupResponseDto> response = groupFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/groups/all");
        model.addAttribute("createNew", "/groups/new");
        model.addAttribute("cardHeader", "All Groups");
        return "pages/groups/group_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "groups");
    }

    @GetMapping("/new")
    public String redirectToNewGroupPage(Model model) {
        model.addAttribute("group", new GroupRequestDto());
        return "pages/groups/group_new";
    }

    @PostMapping("/new")
    public String createNewGroup(@ModelAttribute("group") GroupRequestDto dto) {
        groupFacade.create(dto);
        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        groupFacade.delete(id);
        return "redirect:/groups";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("group", groupFacade.findById(id));
        return "pages/groups/group_details";
    }

    @GetMapping("/update/{id}")
    public String updateGroupPage(@PathVariable Long id, @ModelAttribute("group") GroupRequestDto dto) {
        new_id = id;
        return "pages/groups/group_update";
    }

    @PostMapping("/update")
    public String updateGroup(@ModelAttribute("group") GroupRequestDto dto) {
        groupFacade.update(dto, new_id);
        return "redirect:/groups";
    }
}

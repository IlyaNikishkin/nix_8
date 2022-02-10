package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.view.dto.request.UserRequestDto;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private long new_id;
    private final UserFacade userFacade;
    private final AccountFacade accountFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("accounts", null, null),
            new HeaderName("edit user", null, null),
            new HeaderName("delete user", null, null)
    };
    private final HeaderName[] columnNamesUserAccounts = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("wallet", "wallet", "wallet"),
            new HeaderName("balance, USD", "balance", "balance"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public UserController(UserFacade userFacade, AccountFacade accountFacade) {
        this.userFacade = userFacade;
        this.accountFacade = accountFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        initDataTable(userFacade.findAll(request), columnNames, model);
        model.addAttribute("createUrl", "/users/all");
        model.addAttribute("createNew", "/users/new");
        model.addAttribute("cardHeader", "All Users");
        return "pages/users/user_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "users");
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "pages/users/user_new";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("user") UserRequestDto dto) {
        userFacade.create(dto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userFacade.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/accounts/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountFacade.delete(id);
        return "redirect:/users/{userId}/accounts";
    }

    @GetMapping("/accounts/{id}")
    public String accounts(@PathVariable Long id, Model model) {
        model.addAttribute("user", userFacade.findById(id));
        return "pages/users/user_details";
    }

    @GetMapping("/update/{id}")
    public String updateUserPage(@PathVariable Long id, @ModelAttribute("user") UserRequestDto dto) {
        new_id = id;
        return "pages/users/user_update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserRequestDto dto) {
        userFacade.update(dto, new_id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/accounts")
    public String findAllByUser(Model model, @PathVariable Long id, WebRequest request) {
        model.addAttribute("user", userFacade.findById(id));
        initDataTable(userFacade.findAllAccountsByUser(request, id), columnNamesUserAccounts, model);
        model.addAttribute("createUrl", "/users/" + id + "/accounts");
        model.addAttribute("createNew", "/accounts/new/" + id);
        model.addAttribute("cardHeader", "User " + userFacade.findById(id).getName());
        return "pages/accounts/account_in_user";
    }

    @PostMapping("/{id}/accounts")
    public ModelAndView findAllByUserRedirect(@PathVariable Long id, WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "users/" + id + "/accounts");
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("user", userFacade.findById(id));
        return "pages/users/user_details";
    }
}

package ua.com.alevel.view.controller;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.TransactionFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.response.UserResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/accounts")
public class AccountController extends BaseController {

    private Long accountId;
    private Long update_id;
    private final UserFacade userFacade;
    private final AccountFacade accountFacade;
    private final TransactionFacade transactionFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("wallet", "wallet", "wallet"),
            new HeaderName("balance, USD", "balance", "balance"),
            new HeaderName("user", "user", "user"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };
    private final HeaderName[] transactionColumnNames = new HeaderName[]{
            new HeaderName("date", "date", "date"),
            new HeaderName("sender", "sender", "sender"),
            new HeaderName("sender wallet", null, null),
            new HeaderName("USD", "balance", "balance"),
            new HeaderName("receiver", "receiver", "receiver"),
            new HeaderName("receiver wallet", null, null),
    };

    public AccountController(UserFacade userFacade, AccountFacade accountFacade, TransactionFacade transactionFacade) {
        this.userFacade = userFacade;
        this.accountFacade = accountFacade;
        this.transactionFacade = transactionFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        initDataTable(accountFacade.findAll(request), columnNames, model);
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("createNew", "null");
        model.addAttribute("cardHeader", "All accounts");
        return "pages/accounts/account_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "accounts");
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable @Valid @Min(value = 1, message = "invalid id") @NotNull() Long id, Model model, WebRequest request) {
        accountId = id;
        model.addAttribute("account", accountFacade.findById(id));
        initDataTable(accountFacade.findAllTransactionsByAccount(request, id), transactionColumnNames, model);
        model.addAttribute("createUrl", "/accounts/details/" + id);
        model.addAttribute("createNew", "accountDetails");
        model.addAttribute("cardHeader", "Transaction history");
        AccountRequestDto dto = new AccountRequestDto();
        dto.setMinDate(accountFacade.minDateTransaction(id).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setMaxDate(accountFacade.maxDateTransaction(id).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("export", dto);
        return "pages/accounts/account_details";
    }

    @PostMapping("/details/{id}")
    public ModelAndView findAllTransactionsByAccountRedirect(@PathVariable Long id, WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "accounts/details/" + id);
    }

    @PostMapping("/details/export")
    public String exportToCsv (@ModelAttribute("export") AccountRequestDto dto){
        Long id = accountId;
        accountFacade.exportToCsv(dto,id);
        return "redirect:/accounts/details/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        accountFacade.delete(id);
        return "redirect:/accounts";
    }

    @GetMapping("/update/{id}")
    public String updateAccountPage(@PathVariable @Valid @Min(value = 1, message = "invalid id") @NotNull() Long id, @ModelAttribute("account") AccountRequestDto dto, Model model, WebRequest request) {
        update_id = id;
        PageData<UserResponseDto> users = userFacade.findAll(request);
        model.addAttribute("usersList", userFacade.findAll(request));
        return "pages/accounts/account_update";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.update(dto, update_id);
        return "redirect:/accounts";
    }

    @GetMapping("/new/{id}")
    public String redirectToNewAccountPage(Model model, @PathVariable Long id) {
        AccountRequestDto account = new AccountRequestDto();
        account.setUserId(id);
        model.addAttribute("account", account);
        return "pages/accounts/account_new";
    }

    @PostMapping("/new")
    public String createNewAccount(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.create(dto);
        return "redirect:/users/" + dto.getUserId() + "/accounts";
    }
}

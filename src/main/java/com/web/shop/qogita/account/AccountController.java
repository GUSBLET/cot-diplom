package com.web.shop.qogita.account;

import com.web.shop.qogita.account.dto.LoginDTO;
import com.web.shop.qogita.account.dto.RegistrationDTO;
import com.web.shop.qogita.technical.message.page.MessagePageDTO;
import com.web.shop.qogita.technical.model.attribute.ModelAttributeManager;
import com.web.shop.qogita.technical.model.attribute.ModelPageAttributes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/login-page")
    public String getLoginPage(Model model) {
        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Login")
                .content("login-page")
                .entity(new LoginDTO())
                .build());

        return "layout";
    }

    @GetMapping("/admin/registration")
    public String getRegistrationPage(Model model) {
        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Registration")
                .content("registration-page")
                .entity(new RegistrationDTO())
                .build());

        return "layout";
    }

    @PostMapping("/admin/register")
    public String register(@Valid @ModelAttribute("entity") RegistrationDTO dto, BindingResult bindingResult, Model model) {
        if (accountService.registerNewAccount(dto)) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Success")
                    .content("message-page")
                    .entity(MessagePageDTO.builder()
                            .title("Success")
                            .message("New user added.")
                            .build())
                    .build());
        } else {
            bindingResult.rejectValue("email", "error", "Email has already used, or email is invalid");
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Create new account")
                    .content("registration-page")
                    .entity(dto)
                    .build());
        }
        return "layout";
    }


}

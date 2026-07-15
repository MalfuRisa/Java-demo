package com.example.javademo.controller;


import com.example.javademo.dto.RegisterRequest;
import com.example.javademo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerRequest")
            RegisterRequest registerRequest,
            BindingResult bindingResult
    ) {
        /*
         * @Valid 校验失败时，错误信息会被放进 BindingResult。
         */
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!registerRequest.getPassword()
                .equals(registerRequest.getConfirmPassword())) {

            bindingResult.rejectValue(
                    "confirmPassword",
                    "password.mismatch",
                    "两次输入的密码不一致"
            );

            return "register";
        }

        try {
            userService.register(
                    registerRequest.getUsername(),
                    registerRequest.getPassword()
            );
        } catch (IllegalArgumentException exception) {
            bindingResult.reject(
                    "register.failed",
                    exception.getMessage()
            );

            return "register";
        }

        return "redirect:/login?registered";
    }
}

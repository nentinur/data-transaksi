package project.uts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.uts.entity.User;
import project.uts.service.framework.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public LoginController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String viewPage() {
        return "login";
    }

    @PostMapping
    public String loginUser(
            @ModelAttribute("user") User user,
            HttpServletRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        System.out.println("LoginController.login");
        if (result.hasErrors()) {
            return "login";
        }

        user = userService.login(user, request);
        if (user == null) {
            model.addAttribute(
                    "error", "Login gagal.");
            return "login";
        }
        return "redirect:/index";
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}

package project.uts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.uts.entity.PasswordReset;
import project.uts.entity.PasswordResetToken;
import project.uts.entity.User;
import project.uts.service.framework.PasswordResetTokenService;
import project.uts.service.framework.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequestMapping("/reset-password")
public class ResetPasswordController {
    private final PasswordResetTokenService tokenService;
    private final MessageSource messageSource;
    private final UserService userService;

    @Autowired
    public ResetPasswordController(PasswordResetTokenService tokenService, MessageSource messageSource, UserService userService) {
        this.tokenService = tokenService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping
    public String viewPage(@RequestParam(name = "token", required = false) String token, Model model) {
        PasswordResetToken passwordResetToken = tokenService.findByToken(token);
        if (passwordResetToken == null) {
            model.addAttribute("error", "Tidak dapat menemukan token reset password.");
        } else if (passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token sudah kadaluarsa, silahkan minta reset password baru.");
        } else {
            model.addAttribute("token", passwordResetToken.getToken());
        }
        return "reset-password";
    }

    @PostMapping
    public String resetPassword(@Valid @ModelAttribute("passwordReset")PasswordReset passwordReset,
                                BindingResult result,
                                RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("passwordReset", passwordReset);
            return "redirect:/reset-password?token=" + passwordReset.getToken();
        }
        PasswordResetToken token = tokenService.findByToken(passwordReset.getToken());
        User user = token.getUser();
        user.setPassword(passwordReset.getPassword());
        userService.updatePassword(user);
        return "redirect:/login";
    }

    @ModelAttribute("passwordReset")
    public PasswordReset passwordReset() {
        return new PasswordReset();
    }
}

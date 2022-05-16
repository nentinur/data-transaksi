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
import project.uts.entity.Mail;
import project.uts.entity.PasswordForgot;
import project.uts.entity.PasswordResetToken;
import project.uts.entity.User;
import project.uts.service.framework.EmailService;
import project.uts.service.framework.PasswordResetTokenService;
import project.uts.service.framework.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @Autowired
    public ForgotPasswordController(UserService userService, MessageSource messageSource,
                                    PasswordResetTokenService passwordResetTokenService, EmailService emailService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @GetMapping
    public String viewPage() {

        return "forgot-password";
    }

    @PostMapping
    public String processPasswordForgot(@Valid @ModelAttribute("passwordForgot") PasswordForgot passwordForgot,
                                        BindingResult result,
                                        Model model,
                                        RedirectAttributes attributes,
                                        HttpServletRequest request) {
        if (result.hasErrors()) {
            return "forgot-password";
        }
        User user = userService.findByEmail(passwordForgot.getEmail());
        if (user == null) {
            model.addAttribute("emailError","Alamat email tidak ditemukan!.");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        token = passwordResetTokenService.save(token);
        if (token == null) {
            model.addAttribute("tokenError", messageSource.getMessage("TOKEN_NOT_SAVED", new Object[]{},
                    Locale.ENGLISH));
            return "forgot-password";
        }
        final var mail = new Mail();
        mail.setFrom("no-reply@mohyehia.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", token);
        mailModel.put("user", user);
        mailModel.put("signature", "https://github.com/nentinur/data-transaksi");
        final var url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailModel.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(mailModel);

        emailService.send(mail);
        attributes.addFlashAttribute("success", messageSource.getMessage("PASSWORD_RESET_TOKEN_SENT", new Object[]{},
                Locale.ENGLISH));
        return "redirect:/forgot-password";
    }

    @ModelAttribute("passwordForgot")
    public PasswordForgot passwordForgot() {

        return new PasswordForgot();
    }
}
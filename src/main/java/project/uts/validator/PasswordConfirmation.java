package project.uts.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordConfirmationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmation {
    String password();
    String confirmPassword();
    String message() default "Password must match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordConfirmation[] value();
    }
}

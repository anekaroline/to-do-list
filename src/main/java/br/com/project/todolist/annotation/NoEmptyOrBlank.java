package br.com.project.todolist.annotation;

import br.com.project.todolist.annotation.validation.NoEmptyOrBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoEmptyOrBlankValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoEmptyOrBlank {
    String message() default "The value can't be empty or contain only spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package br.com.project.todolist.annotation;

import br.com.project.todolist.annotation.validation.ISO8601DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISO8601DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISO8601Date {

    String message() default "Formato de data inválido. Use o formato ISO 8601(yyyy-MM-dd).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


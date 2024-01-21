package br.com.project.todolist.annotation.validation;

import br.com.project.todolist.annotation.NoEmptyOrBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoEmptyOrBlankValidator implements ConstraintValidator<NoEmptyOrBlank, CharSequence> {

    @Override
    public void initialize(NoEmptyOrBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null || !value.toString().trim().isEmpty();
    }
}

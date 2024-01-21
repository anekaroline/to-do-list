package br.com.project.todolist.annotation.validation;

import br.com.project.todolist.annotation.ISO8601Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeParseException;

public class ISO8601DateValidator implements ConstraintValidator<ISO8601Date, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;  // Permite valores nulos ou vazios
        }

        try {
            // Tenta fazer o parse da data usando o padrão ISO 8601
            java.time.LocalDate.parse(value);
            return true;  // A data é válida
        } catch (DateTimeParseException e) {
            return false;  // A data não está no formato ISO 8601 válido
        }
    }
}

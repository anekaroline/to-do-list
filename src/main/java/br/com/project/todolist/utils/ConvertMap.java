package br.com.project.todolist.utils;

import br.com.project.todolist.exceptionhandler.exceptions.ObjectErrorSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;

import java.util.Map;
import java.util.Set;

public class ConvertMap {

    public static <T> Object convertMap(Map<String, Object> map, Class<T> clazz) throws InvalidFormatException {
       Object object;
        try {
            object = new ObjectMapper().convertValue(map, clazz);
        }catch (IllegalArgumentException e){
            if (!(e.getCause() instanceof InvalidFormatException)) {
                throw new ObjectErrorSchema("Este body não respeita o esquema.");
            }
            throw (InvalidFormatException) e.getCause();
        }

        validarBean(object);
        return object;
    }

    public static void validarBean(Object request) {
        Set<ConstraintViolation<Object>> violacoes = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(request);

        if (!violacoes.isEmpty()) {
            throw new ConstraintViolationException(violacoes);
        }
    }

}


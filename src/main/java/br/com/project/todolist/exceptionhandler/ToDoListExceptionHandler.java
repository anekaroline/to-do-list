package br.com.project.todolist.exceptionhandler;

import br.com.project.todolist.exceptionhandler.exceptions.EndDateBeforeStartDateException;
import br.com.project.todolist.exceptionhandler.exceptions.ObjectErrorSchema;
import br.com.project.todolist.exceptionhandler.exceptions.WithoutModifications;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ToDoListExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ToDoListExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        String message = messageSource.getMessage("invalid.json", null, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<Erro> errors = getErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String invalidValue = ex.getMessage();
        String message = messageSource.getMessage("invalid.argument", new Object[]{invalidValue}, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    //ObjectErrorSchema
    @ExceptionHandler(ObjectErrorSchema.class)
    public ResponseEntity<Object> handleObjectErrorSchema(ObjectErrorSchema ex, WebRequest request) {
        List<Erro> errors = List.of(new Erro(ex.getMessage()));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ResponseEntity<Object> handleEndDateBeforeStartDateException(EndDateBeforeStartDateException ex, WebRequest request) {
        String message = messageSource.getMessage("end.date.is.after", null, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        String invalidValue = ex.getMessage();
        String message = messageSource.getMessage("not.found", new Object[]{invalidValue}, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(WithoutModifications.class)
    public ResponseEntity<Object> handleWithoutModifications(WithoutModifications ex, WebRequest request) {
        String message = messageSource.getMessage("without.modifications", null, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Erro>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Erro> errors = getErrors(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private List<Erro> getErrors(ConstraintViolationException ex) {
        List<Erro> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String message = violation.getMessage();
            String field = violation.getPropertyPath().toString();
            errors.add(field != null ? new Erro(message, field): new Erro(message));
        }
        return errors;
    }

    private List<Erro> getErrors(BindingResult bindingResult) {
        List<Erro> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errors.add(new Erro(message));
        });
        return errors;
    }

    @Getter
    @Setter
    public static class Erro {
        private String message;
        private String field;

        public Erro(String message) {
            this.message = message;

        }

        public Erro(String message, String field) {
            this.message = message;
            this.field = field;
        }

        @Override
        public String toString() {
            return message;
        }

//        public String toString(String field) {
//            return message + " " + field;
//        }
    }
}

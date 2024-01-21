package br.com.project.todolist.exceptionhandler;

import br.com.project.todolist.exceptionhandler.exceptions.EndDateBeforeStartDateException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = messageSource.getMessage("invalid.json", null, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Erro> errors = getErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    //IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String invalidValue = ex.getMessage();
        String message = messageSource.getMessage("invalid.argument", new Object[]{invalidValue}, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ResponseEntity<Object> handleEndDateBeforeStartDateException(EndDateBeforeStartDateException ex, WebRequest request) {
        String message = messageSource.getMessage("end.date.is.after", null, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    //NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        String invalidValue = ex.getMessage();
        String message = messageSource.getMessage("not.found", new Object[]{invalidValue}, LocaleContextHolder.getLocale());
        List<Erro> errors = List.of(new Erro(message));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
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
    private static class Erro {
        private String message;

        public Erro(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;

        }
    }
}

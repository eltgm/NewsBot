package ru.paradigma.newsbot.conf;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.paradigma.newsbot.exception.TokenNotFoundException;
import ru.paradigma.newsbot.exception.UserAlreadyRegisteredException;
import ru.paradigma.newsbot.exception.UserNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {TokenNotFoundException.class, UserAlreadyRegisteredException.class, UserNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        final var bodyOfResponse = ex.getLocalizedMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}

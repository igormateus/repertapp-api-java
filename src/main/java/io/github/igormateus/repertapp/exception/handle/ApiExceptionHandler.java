package io.github.igormateus.repertapp.exception.handle;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.igormateus.repertapp.exception.DuplicateValidationException;
import io.github.igormateus.repertapp.exception.EntityNotFoundException;
import io.github.igormateus.repertapp.exception.GenericBusinessException;
import io.github.igormateus.repertapp.exception.InvalidJwtTokenException;
import io.github.igormateus.repertapp.exception.InvalidUsernamePasswordSuppliedException;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_USER_MESSAGE = "Internal error. Please try again or contact the system administrator.";

    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ex.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, "Internal Server Error", ex.getMessage()).build();
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }
    
    @ExceptionHandler(InvalidUsernamePasswordSuppliedException.class)
    public ResponseEntity<?> handleInvalidUsernamePasswordSuppliedException(RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ApiError apiError = createApiErrorBuilder(status, "Security Error", ex.getMessage()).build();
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }
    
    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<?> handleInvalidJwtTokenException(RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError apiError = createApiErrorBuilder(status, "Security Error", ex.getMessage()).build();
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError apiError = createApiErrorBuilder(status, "Resource Not Found", exception.getMessage())
                .userMessage(exception.getMessage())
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlerAccessDeniedException(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        ApiError apiError = createApiErrorBuilder(status, "Access Denied", exception.getMessage())
                .userMessage(exception.getMessage())
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> handlerGenericBusinessException(GenericBusinessException exception, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, "Business Exception", detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(exception, exception.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(DuplicateValidationException.class)
    public ResponseEntity<?> handlerDuplicateValidationException(DuplicateValidationException exception, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, "Business Exception", detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, String title, String detail) {
        return ApiError.builder()
                .status(status.value())
                .title(title)
                .detail(detail)
                .userMessage(DEFAULT_USER_MESSAGE)
                .timestamp(OffsetDateTime.now());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception exception, BindingResult bindingResult,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        String detail = "One or more fields are invalid or missing. Please, make sure you're sending the data " +
                "according the API standards and try again.";

        List<ApiError.Object> errorsList = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) name = ((FieldError) objectError).getField();

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        exception.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, "Invalid Data", detail)
                .userMessage(detail)
                .errorObjects(errorsList)
                .build();

        return handleExceptionInternal(exception, apiError, headers, status, request);
    }
}

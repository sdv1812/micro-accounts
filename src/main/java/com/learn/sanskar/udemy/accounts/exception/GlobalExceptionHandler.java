package com.learn.sanskar.udemy.accounts.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    private final HttpServletRequest httpServletRequest;

    @Value("${exception.trace.capture}")
    private Boolean captureTrace;

    public GlobalExceptionHandler(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation issues. Check in 'errors' field in the response."
        );
        List<ErrorResponse.ValidationError> validationErrors =
                ex.getFieldErrors()
                        .stream().map(fieldError -> new ErrorResponse.ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
        errorResponse.setErrors(validationErrors);
        errorResponse.setPath(httpServletRequest.getRequestURI());
        if (captureTrace) errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", ex);
    }


    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus httpStatus, String message, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message
        );
        errorResponse.setPath(httpServletRequest.getRequestURI());
        if (captureTrace) errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}

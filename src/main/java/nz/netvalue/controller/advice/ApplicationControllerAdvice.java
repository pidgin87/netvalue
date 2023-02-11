package nz.netvalue.controller.advice;

import nz.netvalue.controller.model.ErrorResponse;
import nz.netvalue.exception.BadRequestException;
import nz.netvalue.exception.ForbiddenException;
import nz.netvalue.exception.NotModifiedException;
import nz.netvalue.exception.PreconditionFailedException;
import nz.netvalue.exception.ResourceConflictException;
import nz.netvalue.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundHandler(ResourceNotFoundException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestHandler(BadRequestException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(NotModifiedException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ErrorResponse resourceNotModifiedHandler(NotModifiedException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.NOT_MODIFIED.value());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(PreconditionFailedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse preconditionFailedHandler(PreconditionFailedException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse preconditionFailedHandler(ForbiddenException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.FORBIDDEN.value());
        return error;
    }

    @ResponseBody
    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictHandler(ResourceConflictException exception) {
        ErrorResponse error = buildResponse(exception);
        error.setStatus(HttpStatus.CONFLICT.value());
        return error;
    }

    private ErrorResponse buildResponse(Exception exception) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setError(exception.getMessage());
        response.setPath(getCurrentUrl());
        return response;
    }

    private String getCurrentUrl() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getRequestURI();
    }
}

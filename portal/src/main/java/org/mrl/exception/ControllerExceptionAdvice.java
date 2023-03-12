package org.mrl.exception;

import org.mrl.mvc.RestResult;
import org.mrl.mvc.RestResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice("org.mrl.controller")
public class ControllerExceptionAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult<?> handleNoHandlerFoundException(Exception e) {
        return RestResultUtils.failedWithMsg(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<?> handleArgumentException(Exception e) {
        BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        return RestResultUtils.failedWithMsg(HttpStatus.BAD_REQUEST.value(),
                bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(",")));
    }

    @ExceptionHandler(PastryRuntimeException.class)
    public RestResult<Map<String, String>> handleException(Exception e) {
        return RestResultUtils.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<?> handleGlobalException(Exception e) {
        return RestResultUtils.failedWithMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

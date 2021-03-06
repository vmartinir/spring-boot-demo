package org.example.handler;

import org.example.domain.dto.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceException(HttpServletRequest request, ServiceException e) {
        e.printStackTrace();
        return new CommonResult(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult serviceException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return new CommonResult(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage());
    }
}
package com.card.alumni.exception;

import com.card.alumni.common.UnifiedResult;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 全局异常类
 *
 * @author liumingyu
 * @date 2019-11-19 11:15 PM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(value = CaException.class)
    public UnifiedResult handleBusinessException(HttpServletRequest req, CaException ex) {
        LOGGER.error("CaException -> code = {}, message = {}", ex.getCode(), ex.getMessage());

        return UnifiedResult.failure(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public HttpServletResponse handleAuthException(HttpServletRequest req, HttpServletResponse response, Exception e) {
        LOGGER.error("AccessDeniedException -> ", e);
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public UnifiedResult handleException(HttpServletRequest req, Exception ex) {
        LOGGER.error("Exception -> ", ex);

        return UnifiedResult.failure(ResultCodeInterface.FAIL_CODE, ex.getMessage());
    }
}

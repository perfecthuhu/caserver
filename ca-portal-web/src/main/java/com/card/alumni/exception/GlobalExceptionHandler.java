package com.card.alumni.exception;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
    public Object handleBusinessException(HttpServletRequest req, CaException ex) {
        LOGGER.error("CaException -> code = {}, message = {}", ex.getCode(), ex.getMessage());
        Map<String, Object> e = Maps.newHashMap();
        e.put("code", ex.getCode());
        e.put("message", ex.getMessage());
        return e;
    }

    @ExceptionHandler(value = Exception.class)
    public Object handleException(HttpServletRequest req, Exception ex) {
        LOGGER.error("Exception -> ", ex);
        Map<String, Object> e = Maps.newHashMap();
        e.put("code", ResultCodeInterface.FAIL_CODE);
        e.put("message", ex.getMessage());
        return e;
    }
}

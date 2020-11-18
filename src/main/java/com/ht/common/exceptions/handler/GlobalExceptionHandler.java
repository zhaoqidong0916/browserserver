package com.ht.common.exceptions.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.ht.common.exceptions.exception.CountRateLimiterException;
import com.ht.common.exceptions.exception.UploadException;
import com.ht.common.enums.ResponseEnum;
import com.ht.common.page.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.ht.common.enums.ResponseEnum.*;

/**
 * @description: 全局异常捕获处理
 * @author: yaKun.shi
 * @create: 2020-03-05 14:22
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * hibernate validator 数据绑定验证异常拦截
     *
     * @param e 绑定验证异常
     * @return 错误返回消息
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        logger.info("出现错误：{}", e);
        return R.error();
    }

    @ExceptionHandler(UploadException.class)
    public R uploadException(UploadException e) {
        logger.info("出现错误：{}", e);
        return R.result(UPLOAD_ERR);
    }

    /**
     * hibernate validator 数据绑定验证异常拦截
     *
     * @param e 绑定验证异常
     * @return 错误返回消息
     */
    @ExceptionHandler(BindException.class)
    public R validateErrorHandler(BindException e) {
        ObjectError error = e.getAllErrors().get(0);
        logger.info("数据验证异常：{}", error.getDefaultMessage());
        return R.result(LACK_PARAMETERS, error.getDefaultMessage());
    }

    /**
     * hibernate validator 数据绑定验证异常拦截
     *
     * @param e 绑定验证异常
     * @return 错误返回消息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validateErrorHandler(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        logger.info("数据验证异常：{}", error.getDefaultMessage());
        return R.result(LACK_PARAMETERS.setMsg(error.getDefaultMessage()), error.getDefaultMessage());
    }

    /**
     * spring validator 方法参数验证异常拦截
     *
     * @param e 绑定验证异常
     * @return 错误返回消息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R defaultErrorHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        logger.info("数据验证异常：{}", violation.getMessage());
        return R.result(LACK_PARAMETERS.setMsg(violation.getMessage()), violation.getMessage());
    }


    @ExceptionHandler(JsonParseException.class)
    public R jsonParseExceptionHandler(JsonParseException e) {
        logger.info("json参数格式异常", e);
        return R.result(PARAMETERS_ERROR, e.getMessage());

    }

    /**
     * 捕获传入json参数出现参数格式异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R messageHandler(HttpMessageNotReadableException e) {
        logger.info("json参数格式异常", e);
        return R.result(PARAMETERS_ERROR);
    }


    /**
     * 上传文件出现错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public R MultipartHandler(MultipartException e) {
        logger.info("上传文件错误", e);
        return R.result(TIME_OUT);
    }

    /**
     * 限流抛出的自定义异常
     */
    @ExceptionHandler(CountRateLimiterException.class)
    public R countRateLimiterHandler() {
        logger.info("限流抛出异常");
        return R.result(SYSTEM_BUSY);
    }
}

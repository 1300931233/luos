package com.asurplus.common.config;

import com.asurplus.common.enums.StatusEnums;
import com.asurplus.common.exception.ErrorMessageException;
import com.asurplus.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * shiro 未授权异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseResult unauthorizedException(UnauthorizedException e) {
        log.error("Exception: {}", "未授权", e.getMessage());
        return ResponseResult.error(StatusEnums.FORBIDDEN_ERROR);
    }

    /**
     * 参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        e.printStackTrace();
        return new ResponseResult(StatusEnums.PARAMETER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ErrorMessageException.class)
    public ResponseResult IllegalArgumentExceptionHandler(ErrorMessageException e) {
        return new ResponseResult(StatusEnums.ERROR.getCode(), e.getMessage());
    }

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("", e);
        return new ResponseResult(StatusEnums.PARAMETER_ERROR.getCode(), "请求参数 " + e.getParameterName() + " 不能为空");
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("", e);
        return new ResponseResult(StatusEnums.PARAMETER_ERROR.getCode(), "参数体不能为空");
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        //        log.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                String msg = fieldError.getDefaultMessage();
                return new ResponseResult(StatusEnums.PARAMETER_ERROR.getCode(), msg);
            }
        }
        return new ResponseResult(StatusEnums.PARAMETER_ERROR);
    }

    /**
     * 自定义参数错误异常处理器
     *  HttpRequestMethodNotSupportedException 405
     * @param e 自定义参数
     * @return ResponseInfo
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ParamaErrorException.class})
    public ResponseResult paramExceptionHandler(ParamaErrorException e) {
        log.error("", e);
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (!StringUtils.isEmpty(e.getMessage())) {
            return new ResponseResult(StatusEnums.PARAMETER_ERROR.getCode(), e.getMessage());
        }
        return new ResponseResult(StatusEnums.PARAMETER_ERROR);
    }
}

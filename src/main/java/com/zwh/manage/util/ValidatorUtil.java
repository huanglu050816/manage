package com.zwh.manage.util;

import com.zwh.manage.exception.InnerException;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 参数校验工具类
 */
public class ValidatorUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);


    /**
     * 解析校验结果
     * @param bindingResult
     */
    public static void parseValidateResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                // 判断是参数校验失败还是参数非法
                if (!fieldError.getDefaultMessage().contains("Exception")) {
                    throw new InnerException(400, fieldError.getDefaultMessage());
                } else {
                    logger.info("check error: {}", fieldError.getDefaultMessage());
                    throw new InnerException(400, "参数校验非法");
                }
            }
        }
    }

}

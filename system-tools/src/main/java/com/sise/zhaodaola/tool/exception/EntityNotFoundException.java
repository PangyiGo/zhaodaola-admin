package com.sise.zhaodaola.tool.exception;

import org.springframework.util.StringUtils;

/**
 * @Author: PangYi
 * @Date 2020/3/712:51 上午
 * 统一异常处理
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String field, String val) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " does not exist";
    }
}
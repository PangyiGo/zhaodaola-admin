package com.sise.zhaodaola.tool.exception;

import org.springframework.util.StringUtils;

/**
 * @Author: PangYi
 * @Date 2020/3/712:51 上午
 * 统一异常处理
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Class clazz, String field, String val) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " existed";
    }
}
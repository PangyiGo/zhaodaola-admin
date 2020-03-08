package com.sise.zhaodaola.tool.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author: PangYi
 * @Date 2020/3/712:51 上午
 * 异常工具类
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}

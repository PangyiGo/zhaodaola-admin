package com.sise.zhaodaola.tool.dict;

/**
 * @Author: PangYi
 * @Date 2020/3/84:09 下午
 * 系统字典管理
 */
public class DictManager {

    public static String gender(Integer gender) {
        return gender.equals(1) ? "男" : "女";
    }

    public static Boolean isEnable(Integer status) {
        return status.equals(1);
    }

    public static String status(Integer status) {
        return status.equals(1) ? "激活" : "禁用";
    }

    public static String show(Integer status) {
        return status.equals(1) ? "可见" : "隐藏";
    }
}

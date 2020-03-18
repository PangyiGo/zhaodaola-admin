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

    public static String lostStatus(Integer status) {
        String res = "";
        switch (status) {
            case 1:
                res = "寻找中";
                break;
            case 2:
                res = "已寻回";
                break;
            case 3:
                res = "不可见";
                break;
            case 4:
                res = "已删除";
                break;
            default:
                res = "其他";
        }
        return res;
    }

    public static String foundStatus(Integer status) {
        String res = "";
        switch (status) {
            case 1:
                res = "认领中";
                break;
            case 2:
                res = "已认领";
                break;
            case 3:
                res = "不可见";
                break;
            case 4:
                res = "已删除";
                break;
            default:
                res = "其他";
        }
        return res;
    }

    public static String foundType(Integer type) {
        return type.equals(1) ? "个人认领" : "站点认领";
    }
}

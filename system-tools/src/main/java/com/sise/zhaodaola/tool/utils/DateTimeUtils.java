package com.sise.zhaodaola.tool.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.sise.zhaodaola.tool.exception.BadRequestException;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 14:14
 * Description:
 */
public class DateTimeUtils extends DateTime {

    public static DateTime dateTime(String date, String formatter) {
        try {
            return new DateTime(date, formatter);
        } catch (Exception e) {
            throw new BadRequestException("时间格式化异常:" + date);
        }
    }

    public static DateTime beginOfDay(String start) {
        try {
            DateTime dateTime = DateUtil.parse(start);
            return DateUtil.beginOfDay(dateTime);
        } catch (Exception e) {
            throw new BadRequestException("时间格式化异常:" + start);
        }
    }

    public static DateTime endOfDay(String end) {
        try {
            DateTime dateTime = DateUtil.parse(end);
            return DateUtil.endOfDay(dateTime);
        } catch (Exception e) {
            throw new BadRequestException("时间格式化异常:" + end);
        }
    }

}

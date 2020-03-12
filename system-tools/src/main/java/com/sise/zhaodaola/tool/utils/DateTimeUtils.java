package com.sise.zhaodaola.tool.utils;

import cn.hutool.core.date.DateTime;
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

}

package com.sise.zhaodaola.core;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
 * @Author: PangYi
 * @Date 2020/3/81:16 上午
 */
public class JwtTest {

    public static void main(String[] args) {
        DateTime parse = DateUtil.parse("2019-12-12", DatePattern.NORM_DATE_PATTERN);
        System.out.println(parse);
    }
}

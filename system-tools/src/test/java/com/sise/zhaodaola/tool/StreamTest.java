package com.sise.zhaodaola.tool;

import cn.hutool.core.date.DateUtil;
import com.sise.zhaodaola.tool.utils.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/75:40 下午
 */
public class StreamTest {

    public static void main(String[] args) {
        String time = "2019-12-31";

        Date dateTime = DateUtil.parse(time);

        System.out.println(DateUtil.beginOfDay(dateTime));

        System.out.println(DateUtil.endOfDay(dateTime));

        List<String> list = Arrays.asList(StringUtils.split("", ","));

        System.out.println(list);
    }
}

package com.sise.zhaodaola.tool.utils;

import java.util.*;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 11:07
 * Description: 分页工具
 */
public class PageUtils {

    /**
     * 分页结果集封装
     *
     * @param list  数据
     * @param page  当前页数
     * @param size  显示条数
     * @param total 总数
     * @return 信息
     */
    public static PageHelper toPage(List list, long page, long size, long total) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setList(list);
        pageHelper.setPage(page);
        pageHelper.setSize(size);
        pageHelper.setTotal(total);
        return pageHelper;
    }
}

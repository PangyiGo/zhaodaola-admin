package com.sise.zhaodaola.tool.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 11:24
 * Description:
 */
@Data
public class PageHelper {

    private List list = new ArrayList<>(0);

    private long page;

    private long size;

    private long total;
}

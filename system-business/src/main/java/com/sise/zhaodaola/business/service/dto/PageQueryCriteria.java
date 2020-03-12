package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 10:20
 * Description:
 */
@Data
public class PageQueryCriteria implements Serializable {

    private long page;

    private long size;

}

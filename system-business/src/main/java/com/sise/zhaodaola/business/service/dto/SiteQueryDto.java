package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: PangYi
 * @Date 2020/3/1512:02 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SiteQueryDto extends PageQueryCriteria {

    private String word;

    private int status;
}
